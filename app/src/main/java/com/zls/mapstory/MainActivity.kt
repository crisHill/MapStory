package com.zls.mapstory

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zls.mapstory.bean.DrawablePath
import com.zls.mapstory.type.DrawableArea
import com.zls.mapstory.type.TerrainType
import com.zls.mapstory.util.AreaCreator6
import com.zls.mapstory.util.CommonUtil
import com.zls.mapstory.util.Const
import com.zls.mapstory.util.TestData
import com.zls.mapstory.widght.CommonMap

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
    var data: MutableList<DrawableArea> = mutableListOf()
    private fun getData(w: Int = Const.WORLD_W, h: Int = Const.WORLD_H){
        data.clear()

        val sea = DrawablePath(TerrainType.SEA, TestData.createSeaPath(map.measuredWidth, map.measuredHeight),
                Color.GREEN,
                BitmapShader(BitmapFactory.decodeResource(resources, TerrainType.SEA.paintRes), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT))

        //data.add(sea)
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

        val area = w*h/10
        val bound = Rect(10, 10, w - 10, h - 10)
        val creator = AreaCreator6(area, bound, count)
        creator.start()
        val path = CommonUtil.sortedBorders2Path2(creator.path, map.measuredWidth, map.measuredHeight, w, h)
        data.add(DrawablePath(type, path,Color.GREEN/*,
                BitmapShader(BitmapFactory.decodeResource(resources, TerrainType.PLAIN.paintRes), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)*/))

        render()

        //TestData.testStartSortBorders2()
    }
    private fun render(){
        map.viewTreeObserver.addOnDrawListener(listener)
        renderStart = System.currentTimeMillis()
        map.refresh(data)
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