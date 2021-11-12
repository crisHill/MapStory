package com.zls.mapstory

import android.content.Intent
import android.graphics.Rect
import android.graphics.Region
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zls.mapstory.bean.*
import com.zls.mapstory.type.DrawableArea
import com.zls.mapstory.type.TerrainType
import com.zls.mapstory.util.AreaCreator5
import com.zls.mapstory.util.CommonUtil
import com.zls.mapstory.util.Const
import com.zls.mapstory.util.TestData
import com.zls.mapstory.widght.CommonMap
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var map: CommonMap
    private lateinit var et: EditText
    private val context  = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        map = findViewById(R.id.cris_map)
        et = findViewById(R.id.et)

        (findViewById<View>(R.id.data)).setOnClickListener { getData() }
        (findViewById<View>(R.id.second)).setOnClickListener {
            startActivity(Intent(context, SecondActivity::class.java))
        }
    }

    var loadStart: Long = 0
    var renderStart: Long = 0
    var data: DrawableArea? = null
    private fun getData(w: Int = Const.WORLD_W, h: Int = Const.WORLD_H){
        loadStart = System.currentTimeMillis()
        val type: TerrainType = TerrainType.PLAIN
        val count = if (et.text.toString().isEmpty()) 800 else et.text.toString().toInt()

        /*val random = Random(System.currentTimeMillis())
        val lines = mutableListOf<Line>()
        val thick = 8
        for (i in 0 until count){
            lines.add(Line(i * thick, thick / 2, mutableListOf(Pair(100, random.nextInt(200, 1200)))))
        }

        val zone = Zone(lines)
        data = DrawableZone(type,zone)*/

        //data = DrawableRegion(type, Region(20,20,600,600))

        /*val r1 = Region(20,20,600,600)
        val r2 = Region(700,700,900,1200)
        r1.op(r2, Region.Op.UNION)
        data = DrawableSquare(type, r1.boundaryPath)*/

        val area = w*h/4
        val bound = Rect(10, 10, w - 10, h - 10)
        val creator = AreaCreator5(area, bound, count)
        creator.start()
        val path = CommonUtil.sortedBorders2Path(creator.path, creator.step, map.measuredWidth, map.measuredHeight, w, h)
        data = DrawablePath(type, path)

        render()

        //TestData.testStartSortBorders2()
    }
    private fun render(){
        data?.apply {
            map.viewTreeObserver.addOnDrawListener(listener)
            renderStart = System.currentTimeMillis()
            map.refresh(mutableListOf(this))
        }
    }

    private fun removeListener(){
       map.viewTreeObserver.removeOnDrawListener(listener)
    }

    private val listener = ViewTreeObserver.OnDrawListener{
        val now = System.currentTimeMillis()
        println("render cost:${now - renderStart}")
        Toast.makeText(this, "rendered", Toast.LENGTH_SHORT).show()
        map.post {
            removeListener()
        }
    }


}