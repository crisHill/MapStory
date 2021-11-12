package com.zls.mapstory.util

import android.graphics.Point
import android.graphics.Rect
import com.zls.mapstory.bean.Square
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.min
import kotlin.math.sqrt

/**
 * @author criszhai
 * @date 2021/11/4 9:56
 * @desc https://segmentfault.com/a/1190000014167823
 */
class AreaCreator6(area: Int,
                   private val bound: Rect,
                   private var count: Int = 0,
                   private val slimRatioEveryTime: Double = 0.33, private val slimDegree: Int = 3) {

    val points = mutableListOf<Point>()
    private val borders = mutableListOf<Point>()
    val squares = mutableListOf<Square>()
    val path = mutableListOf<Point>()
    //step最小为4，且必须是偶数
    var step = 4

    init {

        if (count <= 0){
            count = min(area, 1000)
        }

        if (area > count){
            val value: Int = ceil(sqrt(1.0 * area / count) + 1).toInt() shr 1
            while (step < value){
                val v = step shl 1
                step = v
            }
        }

        if (step > 1){
            bound.left = floor(1.0 * bound.left / step).toInt() + 1
            bound.top = floor(1.0 * bound.top / step).toInt() + 1

            bound.right = ceil(1.0 * bound.right / step).toInt() - 1
            bound.bottom = ceil(1.0 * bound.bottom / step).toInt() - 1
        }
    }

    fun start() {
        CommonUtil.generateShape2(points, borders, bound, count, slimRatioEveryTime, slimDegree)
        CommonUtil.printPoints("points", points)
        CommonUtil.printPoints("borders", borders)

        CommonUtil.startSortBorders2(points, borders, path, step)
        CommonUtil.printPoints("path", path)
        //CommonUtil.points2Squares(mutableListOf(points), squares)
        //points2Zone()
    }

    /*fun points2Zone(): Zone {
        val map: MutableMap<>
    }*/

}