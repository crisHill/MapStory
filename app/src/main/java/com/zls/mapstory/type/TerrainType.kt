package com.zls.mapstory.type

import com.zls.mapstory.R

/**
 * @author criszhai
 * @date 2021/11/3 13:17
 * @desc
 */
enum class TerrainType(val type: Int, val paintRes: Int) {

    SEA(1, R.mipmap.icon_sea),
    RIVER(2, R.mipmap.icon_river),
    LAKE(3, R.mipmap.icon_sea),
    PLAIN(4, R.mipmap.icon_grassland),
    GRASSLAND(5, R.mipmap.icon_grassland),
    MOUNTAIN(6, R.mipmap.icon_mountain)

}