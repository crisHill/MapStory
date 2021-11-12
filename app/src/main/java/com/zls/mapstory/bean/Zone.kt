package com.zls.mapstory.bean

import android.graphics.Point

/**
 * @author criszhai
 * @date 2021/11/3 13:17
 * @desc
 */
data class Zone(val lines: MutableList<Line>) {

    private lateinit var origin: Point

    init {
        val x = lines[0].x
        val y = lines[0].segment[0].first
        origin = Point(x, y)
    }

}