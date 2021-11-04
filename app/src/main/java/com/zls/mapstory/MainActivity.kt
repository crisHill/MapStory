package com.zls.mapstory

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zls.mapstory.util.BorderCreator
import com.zls.mapstory.util.TestData
import com.zls.mapstory.widght.CrisMap

class MainActivity : AppCompatActivity() {

    lateinit var tv: TextView
    lateinit var crisMap: CrisMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv = findViewById(R.id.tv)
        crisMap = findViewById(R.id.cris_map)

        tv.setOnClickListener { start() }
    }

    private fun start(){
        crisMap.refresh(TestData.createWorldData())

        /*val list = BorderCreator(20, 20, 0, 10, 0, 10).start(0, 0)
        print("list = \n")
        for (p in list){
            print(p.toString() + "\n")
        }*/
    }
}