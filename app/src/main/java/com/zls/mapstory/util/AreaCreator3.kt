package com.zls.mapstory.util

import android.graphics.Point
import android.graphics.Rect
import com.zls.mapstory.bean.Square
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * @author criszhai
 * @date 2021/11/4 9:56
 * @desc https://segmentfault.com/a/1190000014167823
 */
class AreaCreator3(area: Int, private val count: Int,
                   left: Int, top: Int, right: Int, bottom: Int,
                   private val slimRatioEveryTime: Double = 0.33, private val slimDegree: Int = 3) {

    val points = mutableListOf<Point>()
    val borders = mutableListOf<Point>()
    val squares = mutableListOf<Square>()
    val dots = mutableListOf<Square>()
    var step = 1
        private set

    private var l = 0
    private var r = 0
    private var t = 0
    private var b = 0

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
            l = floor(1.0 * left / step).toInt() + 1
            t = floor(1.0 * top / step).toInt() + 1

            r = ceil(1.0 * right / step).toInt() - 1
            b = ceil(1.0 * bottom / step).toInt() - 1
        }
    }

    fun start() {
        execute()

        /*points.clear()
        points.addAll(mutableListOf(
                Point(3, 4), Point(4, 4),
                Point(3, 5), Point(4, 5),
                Point(3, 6), Point(4, 6),
                Point(5, 6), Point(2, 4),
                Point(2, 5), Point(6, 6),
                Point(1, 4), Point(1, 3),
                Point(2, 3), Point(3, 3),
                Point(4, 3), Point(5, 3),
                Point(6, 3), Point(6, 2),
                Point(5, 2), Point(5, 1)))
        borders.clear()
        borders.addAll(mutableListOf(
                Point(4, 4), Point(4, 5),
                Point(3, 6), Point(4, 6),
                Point(5, 6), Point(2, 5),
                Point(6, 6), Point(1, 4),
                Point(1, 3), Point(2, 3),
                Point(3, 3), Point(4, 3),
                Point(5, 3), Point(6, 3),
                Point(6, 2), Point(5, 2), Point(5, 1)))*/

        println("points=")
        for ((i, p) in points.withIndex()){
            print("(${p.x}, ${p.y})")
        }
        print("\n")

        println("borders=")
        for ((i, p) in borders.withIndex()){
            print("(${p.x}, ${p.y})")
        }
        print("\n")

        for (p in points){
            val sq = CommonUtil.onePoint2Square(p)
            if (borders.contains(p)){
                sq.isBorder = true
            }
            dots.add(sq)
        }
        CommonUtil.points2Squares(mutableListOf(points), squares)
        println("squares=")
        for ((i, s) in squares.withIndex()){
            print(s.toString())
        }
        print("\n")
    }

    private fun execute() {
        val fatBound = Rect(l,t,r,b)
        val fatCount = count * 1 / 2
        val random = Random(System.currentTimeMillis())
        CommonUtil.fillWithFat(points, borders, fatBound, fatCount, random)
        println("fatCount=$fatCount, real filled size=${points.size}")

        var remain = count - fatCount
        while (true){
            if (remain < 1){
                return
            }

            var slimCount = (remain * slimRatioEveryTime).toInt()
            if (slimCount <= 8){
                slimCount = remain
            }

            val border = borders[random.nextInt(borders.size)]
            val neighbors = CommonUtil.getNeighbors(border)
            val curSize = points.size
            for (neighbor in neighbors){
                if (CommonUtil.isValid(neighbor, fatBound) && !points.contains(neighbor)){
                    CommonUtil.fillWithSlim(points, borders, mutableListOf(), fatBound, slimCount,
                            neighbor, random, slimDegree)
                    val realSize = points.size - curSize
                    remain -= realSize
                    println("slimCount=$slimCount, real filled size=$realSize")
                    break
                }
            }
        }
    }

}