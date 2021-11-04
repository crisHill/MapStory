package com.zls.mapstory.widght

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.zls.mapstory.bean.Terrain
import com.zls.mapstory.type.TerrainType
import com.zls.mapstory.util.CommonUtil
import java.util.*

/**
 * @author criszhai
 * @date 2021/11/3 17:19
 * @desc
 */
class CrisMap(context: Context, attrs: AttributeSet?): View(context, attrs) {

    val paint: Paint = Paint();
    var terrains: MutableList<Terrain>? = null
    var paintMap: EnumMap<TerrainType, BitmapShader>? = null

    fun refresh(terrains: MutableList<Terrain>){
        this.terrains = terrains

        if (paintMap == null){
            paintMap = EnumMap(TerrainType::class.java)
            for (terrain in TerrainType.values()){
                paintMap!![terrain] =
                    BitmapShader(BitmapFactory.decodeResource(resources, terrain.paintRes), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
            }
        }

        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (terrains != null){
            for (terrain in terrains!!){
                if (terrain.type == TerrainType.PLAIN){
                    paint.shader = null
                    paint.color = Color.RED
                    canvas!!.drawPath(CommonUtil.border2Path(terrain.borderPoints, measuredWidth, measuredHeight), paint)
                    continue
                }
                paint.shader = paintMap!![terrain.type]
                canvas!!.drawPath(CommonUtil.border2Path(terrain.borderPoints, measuredWidth, measuredHeight), paint)
            }
        }
    }

}