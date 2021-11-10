package com.zls.mapstory.util

import com.zls.mapstory.type.TerrainType

/**
 * @author criszhai
 * @date 2021/11/4 9:56
 * @desc
 */
object Const {

    const val BORDER_MIN_POINT = 3
    const val WORLD_W: Int = 10000
    const val WORLD_H: Int = 10000

    val RANDOM_NAME = mutableMapOf(Pair(TerrainType.SEA.type, listOf("anonymous SEA", "endless waters")),
            Pair(TerrainType.RIVER.type, listOf("anonymous RIVER")),
            Pair(TerrainType.LAKE.type, listOf("anonymous LAKE")),
            Pair(TerrainType.PLAIN.type, listOf("anonymous PLAIN")),
            Pair(TerrainType.GRASSLAND.type, listOf("anonymous GRASSLAND")),
            Pair(TerrainType.MOUNTAIN.type, listOf("anonymous MOUNTAIN")))


}