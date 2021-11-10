package com.zls.mapstory

import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zls.mapstory.bean.Terrain
import com.zls.mapstory.type.TerrainType
import com.zls.mapstory.util.*
import com.zls.mapstory.widght.CrisMap

class MainActivity : AppCompatActivity() {

    lateinit var tv: TextView
    lateinit var crisMap: CrisMap
    lateinit var crisMap2: CrisMap
    var show2 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv = findViewById(R.id.tv)
        crisMap = findViewById(R.id.cris_map)
        crisMap2 = findViewById(R.id.cris_map2)

        tv.setOnClickListener { start() }

        val another: View = findViewById(R.id.another)
        another.setOnClickListener {
            show2 = !show2
            crisMap.visibility = if (show2) View.GONE else View.VISIBLE
            crisMap2.visibility = if (show2) View.VISIBLE else View.GONE
        }
    }

    private fun start(w: Int = Const.WORLD_W, h: Int = Const.WORLD_H){
        val sea = TestData.createSea()

        val type: TerrainType = TerrainType.PLAIN
        val area = w * h / 2
        val creator = AreaCreator(area, 10, 0, 0, w, h)
        creator.start(w/2, h/2)
        val squaresLand = Terrain(type, area, creator.squares, w, h, creator.step)
        val dotsLand = Terrain(type, area, creator.dots, w, h, creator.step)

        crisMap.refresh(mutableListOf(sea, squaresLand))
        crisMap2.refresh(mutableListOf(sea, dotsLand))
        //testSlim()
        //testFat()
        //testSort()
        //testEmptyInside()
    }

    private fun testEmptyInside(){
        //val points: MutableList<Point> = mutableListOf(Point(3,3),Point(2,2),Point(3,2),Point(4,2),Point(4,3),Point(4,4),Point(3,4),Point(2,4),Point(2,3))
        //val borders: MutableList<Point> = mutableListOf(Point(2,2),Point(3,2),Point(4,2),Point(4,3),Point(4,4),Point(3,4),Point(2,4),Point(2,3))

        val points: MutableList<Point> = mutableListOf(Point(2,2),Point(4,2),Point(4,3),Point(4,4),Point(3,4),Point(2,4),Point(2,3))
        val borders: MutableList<Point> = mutableListOf(Point(2,2),Point(4,2),Point(4,3),Point(4,4),Point(3,4),Point(2,4),Point(2,3))

        val empty = EmptyJudger.start(Rect(2, 2, 4, 4), points, borders)
        println("empty = $empty")
    }

    private fun testSlim(){
        val list = SlimAreaCreator(20, 20, 0, 10, 0, 10).start(0, 0)
        print("list = \n")
        for (p in list){
            print(p.toString() + "\n")
        }
    }

    private fun testFat(){
        val fat = FatAreaCreator(9, 9, 0, 3, 0, 3)
        fat.start(0, 0)
        fat.printPoints()
        fat.printBorders()
    }
}