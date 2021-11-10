package com.zls.mapstory.util

import android.graphics.Path
import android.graphics.Point
import com.zls.mapstory.bean.Square
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

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
        p.x = p.x / list.size
        p.y = p.y / list.size

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
        var sameX = true
        var minY = list[0].y
        var maxY = minY
        for (index in 0 until list.size - 1){
            minY = min(minY, list[index + 1].y)
            maxY = max(maxY, list[index + 1].y)
            if (list[index].x != list[index + 1].x){
                sameX = false
                break
            }
        }
        if (sameX){
            return Square(Point(list[0].x, minY), 0, maxY - minY)
        }

        var sameY = true
        var minX = list[0].x
        var maxX = minX
        for (index in 0 until list.size - 1){
            minX = min(minX, list[index + 1].x)
            maxX = max(maxX, list[index + 1].x)
            if (list[index].y != list[index + 1].y){
                sameY = false
                break
            }
        }
        if (sameY){
            return Square(Point(minX, list[0].y), maxX - minX, 0)
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

}