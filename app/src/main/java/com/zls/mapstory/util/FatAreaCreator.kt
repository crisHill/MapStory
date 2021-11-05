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
        return borders
    }

    private fun execute(x: Int, y: Int) {
        // 如果当前坐标已被填充，则返回空
        val point = Point(x, y)
        points.add(point)
        borders.add(point)

        val random = Random(System.currentTimeMillis())

        for (index in 1 until count){
            val border = borders[random.nextInt(borders.size)]
            val randomNext = getRandomNeighbor(border.x, border.y, random) {
                isValidPoint(it.x, it.y) && !points.contains(it)
            }
            randomNext?.let {
                points.add(it)
                if (isBorder(it)){
                    borders.add(it)
                }
                updateNeighbors(it)
            }
        }
    }

    private fun updateNeighbors(point: Point){
        borders.removeAll(getNeighbors(point.x, point.y){
            isValidPoint(it.x, it.y) && points.contains(it) && borders.contains(it) && !isBorder(it)
        })
    }

    private fun isBorder(point: Point): Boolean {
        val neighbors = getNeighbors(point.x, point.y){
            isValidPoint(it.x, it.y) && !points.contains(it)
        }
        return neighbors.size > 0
    }

}