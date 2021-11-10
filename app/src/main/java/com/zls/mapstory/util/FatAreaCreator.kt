package com.zls.mapstory.util

import android.graphics.Point
import kotlin.random.Random

/**
 * @author criszhai
 * @date 2021/11/4 9:56
 * @desc https://segmentfault.com/a/1190000014167823
 */
class FatAreaCreator(area: Int, count: Int, left: Int, right: Int, top: Int, bottom: Int): BaseBorderAreaCreator(area,count,left,right, top, bottom) {

    override fun start(x: Int, y: Int): MutableList<Point> {
        execute(x, y)
        //sortBorder(true)
        return borders
    }

    private fun execute(x: Int, y: Int) {
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
            if (!points.contains(item)){
                return true
            }
        }
        return false
    }

}