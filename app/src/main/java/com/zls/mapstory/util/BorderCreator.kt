package com.zls.mapstory.util

import android.graphics.Point
import com.zls.mapstory.type.Direction
import kotlin.math.abs
import kotlin.math.min
import kotlin.random.Random

/**
 * @author criszhai
 * @date 2021/11/4 9:56
 * @desc https://segmentfault.com/a/1190000014167823
 */
class BorderCreator(private val count: Int,
                    private val left: Int, private val right: Int,
                    private val top: Int, private val bottom: Int) {

    private var step: Int = 0
    private val border = mutableListOf<Point>()

    init {
        val d = min(abs(left - right), abs(top - bottom))
        step = if (d >= count) d * 4 / count else 1
    }

    fun start(x: Int, y: Int): MutableList<Point> {
        //slimLoop(x, y)
        slimRecursive(x, y, Random(System.currentTimeMillis()), null)
        return border
    }

    private fun slimRecursive(x: Int, y: Int, random: Random, direction: Direction?): Boolean {
        // 如果当前坐标已被填充，则返回空
        val point = Point(x, y)
        if(border.contains(point)) {
            return false
        }

        // 填充当前坐标
        border.add(point)
        if (direction != null){
            print(direction.desc + "\n")
        }

        // 填充满了则返回当前地图
        if(count <= border.size) {
            return true
        }

        // 随机四个方向的顺序
        val directions = Direction.getShuffledList(random)
        for (dir in directions) {
            val newX = x + dir.deltaX * step
            val newY = y + dir.deltaY * step

            // 判断边界
            if(newX !in left..right || newY !in top..bottom) {
                continue
            }

            // 进入下一层递归并得到结果
            val success = slimRecursive(newX, newY, random, dir)

            // 若结果非空则返回结果
            if(success) return success
        }

        // 状态还原(当需要面积与总面积比例比较大的时候，有可能陷入搜索的死循环（或者说效率特别低）)
        //border.remove(point)

        return false
    }

}