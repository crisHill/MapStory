package com.zls.mapstory.bean

import android.graphics.*
import com.zls.mapstory.type.DrawableArea
import com.zls.mapstory.type.TerrainType

/**
 * @author criszhai
 * @date 2021/11/3 13:17
 * @desc
 */
class DrawableRegion(
        private val terrainType: TerrainType,
        private val region: Region,
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
        val path = region.boundaryPath
        println("path=$path")

        updatePaint(paint)
        val iter = RegionIterator(region)
        val r = Rect()
        while (iter.next(r)) {
            canvas.drawRect(r, paint)
        }
    }

}