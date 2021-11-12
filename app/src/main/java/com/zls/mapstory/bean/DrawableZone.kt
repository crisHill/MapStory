package com.zls.mapstory.bean

import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.zls.mapstory.type.DrawableArea
import com.zls.mapstory.type.TerrainType

/**
 * @author criszhai
 * @date 2021/11/3 13:17
 * @desc
 */
class DrawableZone(
        private val terrainType: TerrainType,
        private val zone: Zone,
        private val color: Int = Color.GREEN,
        private val shader: BitmapShader? = null): DrawableArea {

    override fun getTerrainType(): TerrainType {
        return terrainType
    }

    override fun updatePaint(paint: Paint) {
        if (shader == null){
            paint.color = color
        }else {
            paint.shader = shader
        }
    }

    override fun drawSelf(canvas: Canvas, paint: Paint) {
        updatePaint(paint)
        for (line in zone.lines){
            paint.strokeWidth = line.thick.toFloat()
            val x1 = line.x.toFloat()
            val y1 = line.segment[0].first.toFloat()
            val y2 = y1 + line.segment[0].second.toFloat()
            canvas.drawLine(x1, y1, x1, y2, paint)
        }
    }

}