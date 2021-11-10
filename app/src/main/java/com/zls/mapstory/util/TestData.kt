package com.zls.mapstory.util

import android.graphics.Point
import com.zls.mapstory.bean.Square
import com.zls.mapstory.bean.Terrain
import com.zls.mapstory.type.TerrainType

/**
 * @author criszhai
 * @date 2021/11/4 9:56
 * @desc
 */
object TestData {

    fun createWorldData(): MutableList<Terrain> {
        val terrains: MutableList<Terrain> = mutableListOf()

        terrains.add(createSea())
        terrains.add(createContinent())

        return terrains
    }

    fun createSea(w: Int = Const.WORLD_W, h: Int = Const.WORLD_H): Terrain {
        return Terrain(TerrainType.SEA, w*h, mutableListOf(Square(Point(0,0),w,h)),w,h)
    }

    fun createContinent(showDots: Boolean = false, w: Int = Const.WORLD_W, h: Int = Const.WORLD_H): Terrain {
        val type: TerrainType = TerrainType.PLAIN
        val area = w * h / 2

        val creator = AreaCreator(area, 300, 300, 300, w*3/4, h*4/5)
        creator.start(w/2, h/2)

        return Terrain(type, area, if (showDots) creator.dots else creator.squares, w, h, creator.step)
    }

}