package com.zls.mapstory.util

import android.graphics.Point
import android.graphics.RectF
import android.graphics.Region
import kotlin.math.abs

/**
 * @author criszhai
 * @date 2021/11/4 9:56
 * @desc
 */
object InsideJudger {

    // https://blog.csdn.net/xxssyyyyssxx/article/details/86348486
    fun isInside(point: Point, border: MutableList<Point>): Boolean {
        var iSum = 0
        var x1: Double
        var x2: Double
        var y1: Double
        var y2: Double
        var dLon: Double
        val size = border.size

        for (iIndex in 0 until size step 1){
            x1 = border[iIndex].x.toDouble()
            y1 = border[iIndex].y.toDouble()
            x2 = border[if (iIndex == size - 1) 0 else iIndex + 1].x.toDouble()
            y2 = border[if (iIndex == size - 1) 0 else iIndex + 1].y.toDouble()
            // 以下语句判断A点是否在边的两端点的水平平行线之间，在则可能有交点，开始判断交点是否在左射线上
            if (((point.y >= y1) && (point.y < y2))
                || ((point.y >= y2) && (point.y < y1))) {
                if (abs(y1 - y2) > 0) {
                    //得到 A点向左射线与边的交点的x坐标：
                    dLon = x1 - ((x1 - x2) * (y1 - point.y) ) / (y1 - y2)
                    // 如果交点在A点左侧（说明是做射线与 边的交点），则射线与边的全部交点数加一：
                    if (dLon < point.x) {
                        iSum ++
                    }
                }
            }
        }
        return (iSum % 2) != 0
    }

    /*fun isInsideByPath(point: Point, border: MutableList<Point>): Boolean {
        val path = CommonUtil.border2Path(border)
        val bounds = RectF()
        path.computeBounds(bounds,true)
        val region = Region()
        region.setPath(path, Region(bounds.left.toInt(),bounds.top.toInt(),bounds.right.toInt(),bounds.bottom.toInt()))
        return region.contains(point.x,point.y)
    }*/

}