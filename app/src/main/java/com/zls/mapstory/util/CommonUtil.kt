package com.zls.mapstory.util

import android.graphics.Path
import android.graphics.Point
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

}