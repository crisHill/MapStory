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

    fun testTime(count: Int){
        var listObject: MutableList<Point>? = mutableListOf()
        var now = System.currentTimeMillis()
        for (x in 0 until count){
            listObject!!.add(Point(x, 1))
        }
        println("$count point cost time: ${System.currentTimeMillis() - now}ms")
        listObject = null

        var listNum: MutableList<Int>? = mutableListOf()
        now = System.currentTimeMillis()
        for (x in 0 until count){
            listNum!!.add(x)
        }
        println("$count int cost time: ${System.currentTimeMillis() - now}ms")
        listNum = null
    }

    fun testStartSortBorders(){
        val sorted: MutableList<Point> = mutableListOf()
        val borders: MutableList<Point> = mutableListOf()
        val points: MutableList<Point> = mutableListOf()


        for (x in 3 .. 12){
            borders.add(Point(x, 1))
        }
        for (x in mutableListOf(3,12)){
            borders.add(Point(x, 2))
        }
        for (x in mutableListOf(3,13)){
            borders.add(Point(x, 3))
        }
        for (x in mutableListOf(3,4,5,6,10,11,13)){
            borders.add(Point(x, 4))
        }
        for (x in mutableListOf(7,9,12,13)){
            borders.add(Point(x, 5))
        }
        for (x in mutableListOf<Int>(1,2,3,4,7,9,11,13)){
            borders.add(Point(x, 6))
        }
        for (x in mutableListOf<Int>(1,4,7,9,11,13)){
            borders.add(Point(x, 7))
            borders.add(Point(x, 8))
        }
        for (x in mutableListOf(1,5,6,10,13)){
            borders.add(Point(x, 9))
        }
        for (x in mutableListOf(1,2,13)){
            borders.add(Point(x, 10))
        }
        for (x in mutableListOf(1,3,11,14)){
            borders.add(Point(x, 11))
        }
        for (x in mutableListOf(1,2,8,9,10,12,14)){
            borders.add(Point(x, 12))
        }
        for (x in mutableListOf(3,4,6,7,10,11,13,14)){
            borders.add(Point(x, 13))
        }
        for (x in mutableListOf(1,2,5,7,10,11,12,14)){
            borders.add(Point(x, 14))
        }
        for (x in mutableListOf(1,2,5,7,8,9,10,14)){
            borders.add(Point(x, 15))
        }
        for (x in mutableListOf(1,5,7,10,11,14)){
            borders.add(Point(x, 16))
        }
        for (x in 1..14){
            borders.add(Point(x, 17))
        }
        borders.remove(Point(7, 17))
        borders.remove(Point(10, 17))
        for (x in mutableListOf(1,5,7,10,12)){
            borders.add(Point(x, 18))
        }
        for (x in 1..7){
            borders.add(Point(x, 19))
        }
        points.addAll(borders)
        for (x in 4..11){
            points.add(Point(x, 2))
        }
        for (x in 4..12){
            points.add(Point(x, 3))
        }
        for (x in mutableListOf(7,8,9,12)){
            points.add(Point(x, 4))
        }
        points.add(Point(8, 5))
        for (x in mutableListOf(8,12)){
            points.add(Point(x, 6))
        }
        for (x in mutableListOf(2,3,12)){
            points.add(Point(x, 7))
            points.add(Point(x, 8))
        }
        for (x in mutableListOf(2,3,4,7,8,9,11,12)){
            points.add(Point(x, 9))
        }
        for (x in 3..12){
            points.add(Point(x, 10))
        }
        for (x in mutableListOf(4,5,6,7,8,9,10,12,13)){
            points.add(Point(x, 11))
        }
        for (x in mutableListOf(3,4,5,6,7,13)){
            points.add(Point(x, 12))
        }
        for (x in mutableListOf(5,12)){
            points.add(Point(x, 13))
        }
        for (x in mutableListOf(7,10)){
            points.add(Point(x, 17))
        }
        CommonUtil.startSortBorders(points, borders, sorted)
        print("SortBorders=")
        for (p in sorted){
            print(p)
        }
        println("---end")
    }

    fun testStartSortBorders2(){
        val sorted: MutableList<Point> = mutableListOf()
        val borders: MutableList<Point> = mutableListOf(Point(4, 3),Point(4, 6),Point(6, 3),Point(6, 4),Point(6, 5),Point(6, 6),Point(5, 7),Point(3, 4),Point(5, 2),Point(3, 5),Point(2, 4),Point(1, 4),Point(1, 3),Point(2, 3))
        val points: MutableList<Point> = mutableListOf(Point(4, 3),Point(4, 4),Point(4, 5),Point(4, 6),Point(5, 3),Point(5, 4),Point(5, 5),Point(5, 6),Point(6, 3),Point(6, 4),Point(6, 5),Point(6, 6),Point(5, 7),Point(3, 4),Point(5, 2),Point(3, 5),Point(2, 4),Point(1, 4),Point(1, 3),Point(2, 3))
        CommonUtil.startSortBorders(points, borders, sorted)
        print("SortBorders=")
        for (p in sorted){
            print(p)
        }
        println("---end")
    }

}