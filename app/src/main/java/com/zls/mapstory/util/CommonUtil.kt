package com.zls.mapstory.util

import android.graphics.Path
import android.graphics.Point
import android.graphics.Rect
import com.zls.mapstory.BuildConfig
import com.zls.mapstory.bean.Square
import com.zls.mapstory.type.Direction
import com.zls.mapstory.type.Direction8
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.random.Random

/**
 * @author criszhai
 * @date 2021/11/4 9:56
 * @desc
 */
object CommonUtil {

    fun border2Path(border: MutableList<Point>, uiW: Int, uiH: Int, worldW: Int = Const.WORLD_W, worldH: Int = Const.WORLD_H): Path {
        val path = Path()

        val point = worldPoint2UiPoint(border[0], worldW, worldH, uiW, uiH)
        path.moveTo(point.x.toFloat(), point.y.toFloat())
        for (index in 1 until border.size){
            val uiPoint = worldPoint2UiPoint(border[index], worldW, worldH, uiW, uiH)
            path.lineTo(uiPoint.x.toFloat(), uiPoint.y.toFloat())
        }
        path.close()
        return path
    }

    private fun worldPoint2UiPoint(worldPoint: Point, worldW: Int, worldH: Int, uiW: Int, uiH: Int): Point {
        val x: Int = (uiW * worldPoint.x / worldW.toDouble()).roundToInt()
        val y: Int = (uiH * worldPoint.y / worldH.toDouble()).roundToInt()
        return Point(x, y)
    }

    private fun tryGetGravity4Points(list: MutableList<Point>): Point {
        val p = Point(0,0)
        for (item in list){
            p.x += item.x
            p.y += item.y
        }
        p.x = (1.0 * p.x / list.size).roundToInt()
        p.y = (1.0 * p.y / list.size).roundToInt()

        return if (list.contains(p)){
            p
        }else {
            list[0]
        }
    }

    fun onePoint2Square(p: Point): Square {
        return Square(p, 0, 0)
    }
    private fun twoPoint2Square(list: MutableList<Point>): MutableList<Square> {
        if (list[0].x == list[1].x){
            if (list[0].y - list[1].y == 1){
                return mutableListOf(Square(list[1], 0, 1))
            }else if (list[0].y - list[1].y == -1){
                return mutableListOf(Square(list[0], 0, 1))
            }
        }else if (list[0].y == list[1].y){
            if (list[0].x - list[1].x == 1){
                return mutableListOf(Square(list[1], 1, 0))
            }else if (list[0].x - list[1].x == -1){
                return mutableListOf(Square(list[0], 1, 0))
            }
        }
        return mutableListOf(onePoint2Square(list[0]), onePoint2Square(list[1]))
    }
    private fun tryConvertPoints2Line(list: MutableList<Point>): Square? {
        if (BuildConfig.DEBUG && list.size <= 2) {
            error("Assertion failed")
        }
        var sameX = true
        var minY = list[0].y
        var maxY = minY
        for (index in 0 until list.size - 1) {
            if (list[index].x != list[index + 1].x) {
                sameX = false
                break
            }
            minY = min(minY, list[index + 1].y)
            maxY = max(maxY, list[index + 1].y)
        }
        if (sameX) {
            val yValid = maxY - minY == list.size - 1
            if (yValid){
                return Square(Point(list[0].x, minY), 0, maxY - minY)
            }else {
                return null
            }
        }

        var sameY = true
        var minX = list[0].x
        var maxX = minX
        for (index in 0 until list.size - 1) {
            if (list[index].y != list[index + 1].y) {
                sameY = false
                break
            }
            minX = min(minX, list[index + 1].x)
            maxX = max(maxX, list[index + 1].x)
        }
        if (sameY) {
            val xValid = maxX - minX == list.size - 1
            if (xValid){
                return Square(Point(minX, list[0].y), maxX - minX, 0)
            }else {
                return null
            }
        }

        return null
    }
    private fun getBorderPoints4OriginAndDim(p: Point, dim: Int): MutableList<Point> {
        val list = mutableListOf<Point>()
        for (x in p.x - dim .. p.x + dim){
            list.add(Point(x, p.y - dim))
            list.add(Point(x, p.y + dim))
        }
        for (y in p.y - dim + 1 until p.y + dim){
            list.add(Point(p.x - dim, y))
            list.add(Point(p.x + dim, y))
        }
        return list
    }
    private fun dividePoints4OriginAndDim(points: MutableList<Point>, gravity: Point, dim: Int): MutableList<MutableList<Point>> {
        val result = mutableListOf<MutableList<Point>>()
        val left = mutableListOf<Point>()
        val right = mutableListOf<Point>()
        val top = mutableListOf<Point>()
        val bottom = mutableListOf<Point>()
        for (p in points){
            if (p.x < gravity.x - dim){
                left.add(p)
            }else if (p.x > gravity.x + dim){
                right.add(p)
            }else if (p.y < gravity.y - dim){
                top.add(p)
            }else if (p.y > gravity.y + dim){
                bottom.add(p)
            }
        }
        if (left.size > 0){
            result.add(left)
        }
        if (right.size > 0){
            result.add(right)
        }
        if (top.size > 0){
            result.add(top)
        }
        if (bottom.size > 0){
            result.add(bottom)
        }
        return result
    }
    fun points2Squares(points: MutableList<MutableList<Point>>, squares: MutableList<Square>){
        if (points.size == 0){
            return
        }

        val list = points[0]
        points.remove(list)

        if (list.size == 1){
            squares.add(onePoint2Square(list[0]))
            points2Squares(points, squares)
            return
        }else if (list.size == 2){
            squares.addAll(twoPoint2Square(list))
            points2Squares(points, squares)
            return
        }

        val square = tryConvertPoints2Line(list)
        if (square != null){
            squares.add(square)
            points2Squares(points, squares)
            return
        }

        val gravity = tryGetGravity4Points(list)
        var dim = 0
        while (true){
            dim ++
            val borders = getBorderPoints4OriginAndDim(gravity, dim)
            if (!list.containsAll(borders)){
                dim --
                break
            }
        }
        squares.add(Square(Point(gravity.x - dim, gravity.y - dim), dim * 2, dim * 2))
        val dividedList = dividePoints4OriginAndDim(list, gravity, dim)
        if (dividedList.size > 0){
            points.addAll(dividedList)
        }
        points2Squares(points, squares)
    }

    fun getNeighbors(point: Point, dirs: Array<Direction> = Direction.values(), space: Int = 1): MutableList<Point> {
        val result: MutableList<Point> = mutableListOf()
        for (dir in dirs){
            val next = Point(point.x + dir.deltaX * space, point.y + dir.deltaY * space)
            result.add(next)
        }
        return result
    }

    fun removeFakeBorders(points: MutableList<Point>, borders: MutableList<Point>, bound: Rect, point: Point){
        val neighbors = getNeighbors(point)
        val delete = mutableListOf<Point>()
        for (item in neighbors){
            if (borders.contains(item)){
                val isBorder = isBorder(points, bound, item)
                if (!isBorder){
                    delete.add(item)
                }
            }
        }
        borders.removeAll(delete)
    }

    fun isValid(point: Point, bound: Rect): Boolean {
        return point.x in bound.left .. bound.right && point.y in bound.top .. bound.bottom
    }

    fun isBorder(points: MutableList<Point>, bound: Rect, point: Point): Boolean {
        val neighbors = getNeighbors(point)
        for (item in neighbors){
            if (!isValid(item, bound)){
                return true
            }
            if (!points.contains(item)){
                return true
            }
        }
        return false
    }

    fun fillWithSlim(allPoints: MutableList<Point>, borders: MutableList<Point>,
                     slimPoints: MutableList<Point>, bound: Rect, slimCount: Int,
                     cur: Point, random: Random, slimDegree: Int = 4): Boolean {
        if (slimPoints.size >= slimCount){
            return true
        }

        // 如果当前坐标已被填充，则返回空
        if(allPoints.contains(cur)) {
            return false
        }

        // 填充当前坐标
        allPoints.add(cur)
        slimPoints.add(cur)
        if (isBorder(allPoints, bound, cur)){
            borders.add(cur)
        }
        removeFakeBorders(allPoints, borders, bound, cur)

        // 随机四个方向的顺序
        val directions = Direction.getShuffledList(random)
        val dSize = directions.size
        for (i in 0 until dSize) {
            if (slimDegree in 1..dSize && i >= slimDegree){
                return false
            }
            val dir = directions[i]
            val newX = cur.x + dir.deltaX
            val newY = cur.y + dir.deltaY
            val newCur = Point(newX, newY)

            // 判断边界
            if(!isValid(newCur, bound)) {
                continue
            }

            // 进入下一层递归并得到结果
            val success = fillWithSlim(allPoints,borders,slimPoints,bound,slimCount,newCur,random,slimDegree)

            // 若结果非空则返回结果
            if(success) return success
        }
        // 状态还原(当需要面积与总面积比例比较大的时候，有可能陷入搜索的死循环（或者说效率特别低）)
        //border.remove(point)
        return false
    }

    fun fillWithFat(allPoints: MutableList<Point>, borders: MutableList<Point>,
                    bound: Rect, fatCount: Int, random: Random){
        val dir = Direction8.getByType(random.nextInt(8) + 1)
        val offsetX = random.nextInt(bound.right - bound.left) / 3
        val offsetY = random.nextInt(bound.bottom - bound.top) / 3
        val centerX = (bound.left + bound.right) / 2
        val centerY = (bound.top + bound.bottom) / 2
        val origin = Point(centerX + dir!!.deltaX * offsetX, centerY + dir.deltaY * offsetY)

        allPoints.add(origin)
        borders.add(origin)

        for (index in 1 until fatCount){
            val borderIndex = random.nextInt(borders.size)
            val border = borders[borderIndex]
            val neighbors = getNeighbors(border)
            for (item in neighbors){
                val valid = isValid(item, bound)
                if (valid){
                    val empty = !allPoints.contains(item)
                    if (empty){
                        allPoints.add(item)
                        if (isBorder(allPoints, bound, item)){
                            borders.add(item)
                        }
                        removeFakeBorders(allPoints, borders, bound, item)
                        break
                    }
                }
            }
        }
    }

}