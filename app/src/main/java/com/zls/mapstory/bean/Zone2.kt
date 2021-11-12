package com.zls.mapstory.bean

/**
 * @author criszhai
 * @date 2021/11/3 13:17
 * @desc
 */
data class Zone2(val l: Int, val t: Int, val r: Int, val b: Int) {

    val w: Int = r - l
    val h: Int = b - t

}