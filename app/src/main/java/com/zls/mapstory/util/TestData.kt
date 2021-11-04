package com.zls.mapstory.util

import android.graphics.Point
import com.zls.mapstory.bean.Terrain
import com.zls.mapstory.type.TerrainType
import kotlin.math.min
import kotlin.random.Random

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

    private fun createSea(w: Int = Const.WORLD_W, h: Int = Const.WORLD_H): Terrain {
        val terrain = Terrain(TerrainType.SEA, "pacific ocean", mutableListOf())
        val random = Random(System.currentTimeMillis())
        val sp = min(w, h) / 200
        for (x in 10 until w - 10 step sp){
            terrain.borderPoints.add(Point(x, random.nextInt(sp * 5)))
        }
        for (y in 10 until h - 10 step sp){
            terrain.borderPoints.add(Point(w - random.nextInt(sp * 5), y))
        }
        for (x in w - 10 downTo 10 step sp){
            terrain.borderPoints.add(Point(x, h - random.nextInt(sp * 5)))
        }
        for (y in h - 10 until 10 step sp){
            terrain.borderPoints.add(Point(random.nextInt(sp * 5), y))
        }
        return terrain
    }

    private fun createContinent(w: Int = Const.WORLD_W, h: Int = Const.WORLD_H): Terrain {
        val terrain = Terrain(TerrainType.PLAIN, "ASIAN", mutableListOf())
        terrain.borderPoints.addAll(BorderCreator(200, 100, w  - 100, 200, h - 200).start(w / 2, h / 2))
        return terrain
    }

}