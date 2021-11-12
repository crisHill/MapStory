package com.zls.mapstory.type

import android.graphics.Canvas
import android.graphics.Paint

/**
 * @author criszhai
 * @date 2021/11/12 12:08
 * @desc
 */
interface DrawableArea {

    fun getTerrainType(): TerrainType
    fun updatePaint(paint: Paint)
    fun drawSelf(canvas: Canvas, paint: Paint)

}