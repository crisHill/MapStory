package com.zls.mapstory

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zls.mapstory.bean.Terrain
import com.zls.mapstory.type.TerrainType
import com.zls.mapstory.util.AreaCreator2
import com.zls.mapstory.util.Const
import com.zls.mapstory.util.TestData
import com.zls.mapstory.widght.CrisMap

class MainActivity : AppCompatActivity() {

    private lateinit var crisMap: CrisMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        crisMap = findViewById(R.id.cris_map)

        (findViewById<View>(R.id.data)).setOnClickListener { getData() }
        (findViewById<View>(R.id.square)).setOnClickListener {
            curDot = false
            render()
        }
        (findViewById<View>(R.id.dot)).setOnClickListener {
            curDot = true
            render()
        }
    }

    var curDot = true
    var seaTerrain = TestData.createSea()
    var squareTerrain: Terrain? = null
    var dotTerrain: Terrain? = null
    private fun getData(w: Int = Const.WORLD_W, h: Int = Const.WORLD_H){
        val type: TerrainType = TerrainType.PLAIN
        val area = w * h / 4
        val creator = AreaCreator2(area, 2000, w/8, h/8, w*7/8, h*7/8)
        creator.start(w/2, h/2)
        squareTerrain = Terrain(type, area, creator.squares, w, h, creator.step)
        dotTerrain = Terrain(type, area, creator.dots, w, h, creator.step)
        render()
    }
    private fun render(){
        crisMap.refresh(mutableListOf(seaTerrain, if (curDot) dotTerrain!! else squareTerrain!!))
    }


}