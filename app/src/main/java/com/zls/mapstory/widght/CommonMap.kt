package com.zls.mapstory.widght

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.zls.mapstory.type.DrawableArea

/**
 * @author criszhai
 * @date 2021/11/3 17:19
 * @desc
 */
class CommonMap(context: Context, attrs: AttributeSet?): View(context, attrs) {

    private var areas: MutableList<DrawableArea>? = null
    private val paint: Paint = Paint()

    fun refresh(areas: MutableList<DrawableArea>? = null){
        this.areas = areas
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.also {
            areas?.apply {
                for (r in this){
                    r.drawSelf(it, paint)
                }
            }
        }
    }

}