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
class AreaCreator(area: Int, private val count: Int, left: Int, top: Int, right: Int, bottom: Int) {

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
            l = floor(1.0 * left / step).toInt()
            t = floor(1.0 * top / step).toInt()

            r = ceil(1.0 * right / step).toInt()
            b = ceil(1.0 * bottom / step).toInt()
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
        val justTest = true
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

            points.addAll(borders)
            points.add(Point(4,2))
            return
        }



        // 如果当前坐标已被填充，则返回空
        val point = Point(x, y)
        points.add(point)
        borders.add(point)

        val random = Random(System.currentTimeMillis())

        for (index in 1 until count){
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