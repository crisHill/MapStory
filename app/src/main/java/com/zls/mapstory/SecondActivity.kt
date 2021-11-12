package com.zls.mapstory

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zls.mapstory.bean.Terrain
import com.zls.mapstory.type.TerrainType
import com.zls.mapstory.util.AreaCreator4
import com.zls.mapstory.util.Const
import com.zls.mapstory.util.TestData
import com.zls.mapstory.widght.CrisMap

class SecondActivity : AppCompatActivity() {

    private lateinit var crisMap: CrisMap
    private lateinit var et: EditText
    private lateinit var square: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        crisMap = findViewById(R.id.cris_map)
        et = findViewById(R.id.et)

        (findViewById<View>(R.id.data)).setOnClickListener { getData() }
        square = findViewById(R.id.square)
        square.setOnClickListener {
            curDot = !curDot
            square.text = if (curDot) "dot" else "square"
            render()
        }
        square.text = if (curDot) "dot" else "square"
    }

    var curDot = true
    var seaTerrain = TestData.createSea()
    var squareTerrain: Terrain? = null
    var dotTerrain: Terrain? = null
    var loadStart: Long = 0
    var renderStart: Long = 0
    private fun getData(w: Int = Const.WORLD_W, h: Int = Const.WORLD_H){
        loadStart = System.currentTimeMillis()
        val type: TerrainType = TerrainType.PLAIN
        val area = w * h / 4
        val count = if (et.text.toString().isEmpty()) 800 else et.text.toString().toInt()
        val creator = AreaCreator4(1000000, count, 10, 10, w - 10, h - 10)
        creator.start()
        squareTerrain = Terrain(type, area, creator.squares, w, h, creator.step)
        dotTerrain = Terrain(type, area, creator.dots, w, h, creator.step)
        Toast.makeText(this, "${creator.points.size} points prepared", Toast.LENGTH_SHORT).show()
        val now = System.currentTimeMillis()
        println("getData cost:${now - loadStart}")
        render()
    }
    private fun render(){
        crisMap.viewTreeObserver.addOnDrawListener(listener)
        renderStart = System.currentTimeMillis()
        crisMap.refresh(mutableListOf(seaTerrain, if (curDot) dotTerrain!! else squareTerrain!!))
    }

    private fun removeListener(){
       crisMap.viewTreeObserver.removeOnDrawListener(listener)
    }

    private val listener = ViewTreeObserver.OnDrawListener{
        val now = System.currentTimeMillis()
        println("render cost:${now - renderStart}")
        Toast.makeText(this, "rendered", Toast.LENGTH_SHORT).show()
        crisMap.post {
            removeListener()
        }
    }


}