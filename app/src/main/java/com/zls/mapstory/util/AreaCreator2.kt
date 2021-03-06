package com.zls.mapstory.util

import android.graphics.Point
import com.zls.mapstory.bean.Square
import com.zls.mapstory.type.Direction
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * @author criszhai
 * @date 2021/11/4 9:56
 * @desc https://segmentfault.com/a/1190000014167823
 */
class AreaCreator2(area: Int, private val count: Int, left: Int, top: Int, right: Int, bottom: Int) {

    private val points = mutableListOf<Point>()
    private val borders = mutableListOf<Point>()
    val squares = mutableListOf<Square>()
    val dots = mutableListOf<Square>()
    var step = 1
        private set

    var l = 0
    var r = 0
    var t = 0
    var b = 0

    init {
        if (area > count * 4){
            val value: Double = sqrt(1.0 * area / count)
            step = if (value.toInt() % 2 == 1){
                ceil(value).toInt()
            }else {
                floor(value).toInt()
            }
        }

        if (step > 1){
            l = ceil(1.0 * left / step).toInt() + 1
            t = ceil(1.0 * top / step).toInt() + 1

            r = floor(1.0 * right / step).toInt() - 1
            b = floor(1.0 * bottom / step).toInt() - 1
        }
    }

    fun start(x: Int, y: Int) {
        execute(x/step, y/step)
        for (p in points){
            val sq = CommonUtil.onePoint2Square(p)
            if (borders.contains(p)){
                sq.isBorder = true
            }
            dots.add(sq)
        }
        CommonUtil.points2Squares(mutableListOf(points), squares)
    }

    private fun isValidPoint(x: Int, y: Int): Boolean {
        return x in l..r && y in t..b
    }

    private fun getNeighbors(x: Int, y: Int): MutableList<Point> {
        val result: MutableList<Point> = mutableListOf()
        val dirs = Direction.values()
        for (dir in dirs){
            val next = Point(x + dir.deltaX, y + dir.deltaY)
            result.add(next)
        }
        return result
    }

    private fun execute(x: Int, y: Int) {
        val justTest = false
        if (justTest){
            borders.add(Point(4,1))
            borders.add(Point(1,2))
            borders.add(Point(2,2))
            borders.add(Point(3,2))
            borders.add(Point(5,2))
            borders.add(Point(2,3))
            borders.add(Point(3,3))
            borders.add(Point(4,3))
            borders.add(Point(5,3))
            borders.add(Point(1,4))
            borders.add(Point(2,4))

            points.addAll(borders)
            points.add(Point(4,2))
            return
        }
        val dotCount = count * 1 / 3
        val random = Random(System.currentTimeMillis())
        fillWithFat(dotCount, x, y)
        var secondP: Point? = null
        while (true){
            val secondX = random.nextInt(r - l) + l
            val secondY = random.nextInt(b - t) + t
            val tempP = Point(secondX, secondY)
            if (!points.contains(tempP)){
                secondP = tempP
                break
            }
        }
        if (secondP == null){
            return
        }
        fillWithFat(dotCount, secondP.x, secondP.y)

        var remain = count - dotCount - dotCount
        while (true){
            if (remain < 1){
                return
            }

            val slimCount = if (remain <= 8){
                remain
            }else {
                (remain + 1) / 4
            }
            remain -= slimCount
            val borderIndex = random.nextInt(borders.size)
            val border = borders[borderIndex]
            val neighbors = getNeighbors(border.x, border.y)
            for (neighbor in neighbors){
                if (isValidPoint(neighbor.x, neighbor.y) && !points.contains(neighbor)){
                    fillWithSlim(mutableListOf(), slimCount, neighbor.x, neighbor.y, random, null)
                    break
                }
            }
        }
    }

    private fun fillWithSlim(slimPoint: MutableList<Point>, slimCount: Int, x: Int, y: Int, random: Random, direction: Direction?): Boolean {
        // ?????????????????????????????????????????????
        val point = Point(x, y)
        if(slimPoint.contains(point)) {
            return false
        }

        // ??????????????????
        slimPoint.add(point)
        if (isBorder(point)){
            borders.add(point)
        }
        updateNeighbors(x, y)
        if (direction != null){
            print(direction.desc + "\n")
        }

        // ?????????????????????????????????
        if(slimCount <= slimPoint.size) {
            points.addAll(slimPoint)
            return true
        }

        // ???????????????????????????
        val directions = Direction.getShuffledList(random)
        for (i in 0 until directions.size) {
            if (i == 2){
                return false
            }
            val dir = directions[i]
            val newX = x + dir.deltaX
            val newY = y + dir.deltaY

            // ????????????
            if(!isValidPoint(newX, newY)) {
                continue
            }

            // ????????????????????????????????????

            val success = fillWithSlim(slimPoint, slimCount, newX, newY, random, dir)

            // ??????????????????????????????
            if(success) return success
        }

        // ????????????(?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????)
        //border.remove(point)

        return false
    }

    private fun fillWithFat(dotCount: Int, x: Int, y: Int){
        // ?????????????????????????????????????????????
        val point = Point(x, y)
        points.add(point)
        borders.add(point)

        val random = Random(System.currentTimeMillis())

        for (index in 1 until dotCount){
            val borderIndex = random.nextInt(borders.size)
            val border = borders[borderIndex]

            val neighbors = getNeighbors(border.x, border.y)
            for (item in neighbors){
                val valid = isValidPoint(item.x, item.y)
                if (valid){
                    val empty = !points.contains(item)
                    if (empty){
                        points.add(item)
                        if (isBorder(item)){
                            borders.add(item)
                        }
                        updateNeighbors(item.x, item.y)
                        break
                    }
                }
            }
        }
    }

    private fun updateNeighbors(x: Int, y: Int){
        val neighbors = getNeighbors(x, y)
        val delete = mutableListOf<Point>()
        for (item in neighbors){
            if (borders.contains(item)){
                val isBorder = isBorder(item)
                if (!isBorder){
                    delete.add(item)
                }
            }
        }
        borders.removeAll(delete)
    }

    private fun isBorder(point: Point): Boolean {
        val neighbors = getNeighbors(point.x, point.y)
        for (item in neighbors){
            if (!isValidPoint(item.x, item.y)){
                return true
            }
            if (!points.contains(item)){
                return true
            }
        }
        return false
    }

}