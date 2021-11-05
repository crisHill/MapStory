package com.zls.mapstory.util

import android.graphics.Point
import com.zls.mapstory.type.Direction
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * @author criszhai
 * @date 2021/11/4 9:56
 * @desc https://segmentfault.com/a/1190000014167823
 */
abstract class BaseBorderAreaCreator(area: Int, protected val count: Int,
                                     protected val left: Int, protected val right: Int,
                                     protected val top: Int, protected val bottom: Int) {

    protected var step: Int = 0
    protected val points = mutableListOf<Point>()
    protected val borders = mutableListOf<Point>()

    init {
        step = if (area >= count) sqrt((area  / count).toDouble()).toInt() else 1
    }

    abstract fun start(x: Int, y: Int): MutableList<Point>

    protected fun isValidPoint(x: Int, y: Int): Boolean {
        return x in left..right && y in top..bottom
    }

    protected fun getNeighbors(x: Int, y: Int, filter: (p: Point) -> Boolean): MutableList<Point> {
        val result: MutableList<Point> = mutableListOf()
        val dirs = Direction.values()
        for (dir in dirs){
            val next = Point(x + dir.deltaX * step, y + dir.deltaY * step)
            if(filter.invoke(next)) {
                result.add(next)
                break
            }
        }
        return result
    }

    protected fun getRandomNeighbor(x: Int, y: Int, random: Random, filter: (p: Point) -> Boolean): Point? {
        val dirs = Direction.getShuffledList(random)
        for (dir in dirs){
            val next = Point(x + dir.deltaX * step, y + dir.deltaY * step)
            if(filter.invoke(next)) {
                return next
            }
        }
        return null
    }

}