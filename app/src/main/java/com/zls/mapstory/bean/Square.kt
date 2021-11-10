package com.zls.mapstory.bean

import android.graphics.Color
import android.graphics.Point

/**
 * @author criszhai
 * @date 2021/11/3 13:17
 * @desc
 */
data class Square(val origin: Point, val width: Int, val height: Int) {

    val area: Int = width * height

    var isBorder = false

}