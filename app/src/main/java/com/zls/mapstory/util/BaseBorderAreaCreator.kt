package com.zls.mapstory.util

import android.graphics.Point
import com.zls.mapstory.type.Direction
import com.zls.mapstory.type.Direction8
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

    fun printPoints(){
        print("points = \n")
        for (p in points){
            print(p.toString() + "\n")
        }
    }

    fun printBorders(){
        print("borders = \n")
        for (p in borders){
            print(p.toString() + "\n")
        }
    }

    abstract fun start(x: Int, y: Int): MutableList<Point>

    protected fun isValidPoint(x: Int, y: Int): Boolean {
        return x in left..right && y in top..bottom
    }

    protected fun getNeighbors(x: Int, y: Int): MutableList<Point> {
        val result: MutableList<Point> = mutableListOf()
        val dirs = Direction.values()
        for (dir in dirs){
            val next = Point(x + dir.deltaX * step, y + dir.deltaY * step)
            result.add(next)
        }
        return result
    }

    protected fun getNeighbors(x: Int, y: Int, filter: (p: Point) -> Boolean): MutableList<Point> {
        val result: MutableList<Point> = mutableListOf()
        val dirs = Direction.values()
        for (dir in dirs){
            val next = Point(x + dir.deltaX * step, y + dir.deltaY * step)
            if(filter.invoke(next)) {
                result.add(next)
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

    protected fun sortBorder(clockDirection: Boolean) {
        if (borders.size == 0){
            return
        }

        val tempList = mutableListOf<Point>()
        tempList.addAll(borders)
        borders.clear()

        var point = tempList.removeAt(0)
        borders.add(point)

        val dirs = Direction8.values()
        var dir: Direction8 = dirs[0]

        while (tempList.size > 0){
            var next = Point(point.x + dir.deltaX * step, point.y + dir.deltaY * step)
            while (!tempList.contains(next)){
                dir = if (clockDirection) dir.next() else dir.pre()
                next = Point(point.x + dir.deltaX * step, point.y + dir.deltaY * step)
            }
            tempList.remove(next)
            borders.add(next)
            point = next
        }
    }

}