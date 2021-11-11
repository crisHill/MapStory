package com.zls.mapstory.bean

import android.graphics.Point

/**
 * @author criszhai
 * @date 2021/11/3 13:17
 * @desc
 */
data class Square(val origin: Point, val width: Int, val height: Int) {

    val area: Int = width * height

    var isBorder = false

    override fun toString(): String {
        return "(x=${origin.x},y=${origin.y},w=$width,h=$height)"
    }

}