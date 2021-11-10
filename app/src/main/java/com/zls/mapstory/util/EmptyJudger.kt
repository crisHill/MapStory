package com.zls.mapstory.util

import android.graphics.Point
import android.graphics.Rect


/**
 * @author criszhai
 * @date 2021/11/4 9:56
 * @desc 判断给定的点会不会导致点集形成空泡
 */
object EmptyJudger {

    // true 会形成空泡
    fun start(rect: Rect, points: MutableList<Point>, borders: MutableList<Point>): Boolean {
        for (x in rect.left..rect.right){
            for (y in rect.top..rect.bottom){
                val p = Point(x, y)
                if (points.contains(p)){
                    continue
                }
                val inside = contains(p, borders)
                if (inside){
                    return true
                }
            }
        }

        return false
    }

    fun contains(point: Point, borders: MutableList<Point>): Boolean {
        val N: Int = borders.size
        val boundOrVertex = true //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true

        var intersectCount = 0 //cross points count of x

        val precision = 2e-10 //浮点类型计算时候与0比较时候的容差

        var p1: Point
        var p2: Point

        val p = point //当前点


        p1 = borders[0]

        for (i in 1..N) { //check all rays
            if (p == p1) {
                return boundOrVertex //p is an vertex
            }
            p2 = borders[i % N] //right vertex
            if (p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)) { //ray is outside of our interests
                p1 = p2
                continue  //next ray left point
            }
            if (p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)) { //横坐标 内  ray is crossing over by the algorithm (common part of)
                if (p.y <= Math.max(p1.y, p2.y)) { //y下  x is before of ray
                    if (p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)) { //overlies on a horizontal ray  垂线
                        return boundOrVertex
                    }
                    if (p1.y == p2.y) { //水平线 ray is vertical
                        if (p1.y == p.y) { //水平线内 overlies on a vertical ray
                            return boundOrVertex
                        } else { //before ray
                            ++intersectCount //交点在上方
                        }
                    } else { //cross point on the left side
                        val xinters: Double = ((p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y).toDouble()
                        if (Math.abs(p.y - xinters) < precision) { //== 0  在线上  overlies on a ray
                            return boundOrVertex
                        }
                        if (p.y < xinters) { //before ray
                            ++intersectCount //交点在上方
                        }
                    }
                }
            } else { //special case when ray is crossing through the vertex
                if (p.x == p2.x && p.y <= p2.y) { //p crossing over p2
                    val p3 = borders.get((i + 1) % N) //next vertex
                    if (p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)) { //p.x lies between p1.x & p3.x
                        ++intersectCount
                    } else {
                        intersectCount += 2
                    }
                }
            }
            p1 = p2 //next ray left point
        }

        return if (intersectCount % 2 == 0) { //偶数在多边形外
            false
        } else { //奇数在多边形内
            true
        }
    }

}