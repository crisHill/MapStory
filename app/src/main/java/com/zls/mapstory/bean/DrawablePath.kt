package com.zls.mapstory.bean

import android.graphics.*
import com.zls.mapstory.type.DrawableArea
import com.zls.mapstory.type.TerrainType

/**
 * @author criszhai
 * @date 2021/11/3 13:17
 * @desc
 */
class DrawablePath(
        private val terrainType: TerrainType,
        private val path: Path,
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
        canvas.drawPath(path, paint)
    }

}