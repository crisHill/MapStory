package com.zls.mapstory.bean

import android.graphics.Point
import com.zls.mapstory.type.TerrainType

/**
 * @author criszhai
 * @date 2021/11/3 13:17
 * @desc
 */
data class Terrain(val type: TerrainType, val name: String, val borderPoints: MutableList<Point>) {

}