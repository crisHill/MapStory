package com.zls.mapstory.bean

import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.zls.mapstory.type.DrawableArea
import com.zls.mapstory.type.TerrainType
import com.zls.mapstory.util.Const

/**
 * @author criszhai
 * @date 2021/11/3 13:17
 * @desc
 */
class DrawableSquare(
        private val terrainType: TerrainType,
        private val step: Int,
        private val squares: MutableList<Square>,
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
        val uiW = canvas.width
        val uiH = canvas.height
        val fullX = Const.WORLD_W
        val fullY = Const.WORLD_H
        for (sq in squares){
            val xRatio = 1.0f * uiW / fullX
            val yRatio = 1.0f * uiH / fullY
            val l = (sq.origin.x * step - step / 2) * xRatio
            val t = (sq.origin.y * step - step / 2) * yRatio
            val r = ((sq.origin.x + sq.width) * step + step / 2) * xRatio
            val b = ((sq.origin.y + sq.height) * step + step / 2) * yRatio

            canvas.drawRect(l, t, r, b, paint)
        }
    }

}