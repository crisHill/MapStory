package com.zls.mapstory.bean

import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.zls.mapstory.type.TerrainType
import com.zls.mapstory.util.Const

/**
 * @author criszhai
 * @date 2021/11/3 13:17
 * @desc
 */
data class Terrain(val type: TerrainType,
                   val area: Int,
                   val squares: MutableList<Square> = mutableListOf(),
                   val fullX: Int = Const.WORLD_W,
                   val fullY: Int = Const.WORLD_H,
                   val step: Int = 1,
                   val name: String = type.getRandomName()) {

    val showBorder = true
    val showSpace = false

    fun drawSelfWithSpace(uiW: Int, uiH: Int, paint: Paint, canvas: Canvas, shader: BitmapShader) {
        if (squares.size == 0){
            return
        }
        for (sq in squares){
            if (step == 1 && sq.width * sq.height == 0){
                continue
            }
            val l = sq.origin.x * step - step / 2
            val t = sq.origin.y * step - step / 2
            val r = l + sq.width * step + step / 2
            val b = t + sq.height * step + step / 2
            val xRatio = 1.0f * uiW / fullX
            val yRatio = 1.0f * uiH / fullY
            if (sq.isBorder && showBorder){
                paint.shader = null
                paint.color = Color.RED
            }else {
                paint.shader = shader
            }
            canvas.drawRect(l * xRatio, t * yRatio, r * xRatio, b * yRatio, paint)
        }

    }

    fun drawSelf(uiW: Int, uiH: Int, paint: Paint, canvas: Canvas, shader: BitmapShader) {
        if (showSpace){
            drawSelfWithSpace(uiW, uiH, paint, canvas, shader)
            return
        }
        if (squares.size == 0){
            return
        }
        for (sq in squares){
            if (step == 1 && sq.width * sq.height == 0){
                continue
            }
            val xRatio = 1.0f * uiW / fullX
            val yRatio = 1.0f * uiH / fullY
            val l = (sq.origin.x * step - step / 2) * xRatio
            val t = (sq.origin.y * step - step / 2) * yRatio
            val r = ((sq.origin.x + sq.width) * step + step / 2) * xRatio
            val b = ((sq.origin.y + sq.height) * step + step / 2) * yRatio
            if (sq.isBorder && showBorder){
                paint.shader = null
                paint.color = Color.RED
            }else {
                paint.shader = shader
            }
            //Log.i("drawSelf", "(${sq.origin.x},${sq.origin.y},${sq.width},${sq.height})->($l, $t, $r, $b)")
            canvas.drawRect(l, t, r, b, paint)
        }

    }

}