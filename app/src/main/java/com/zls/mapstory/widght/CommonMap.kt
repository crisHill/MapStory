package com.zls.mapstory.widght

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.zls.mapstory.listener.BaseGestureListener
import com.zls.mapstory.type.DrawableArea

/**
 * @author criszhai
 * @date 2021/11/3 17:19
 * @desc
 */
class CommonMap(context: Context, attrs: AttributeSet?): View(context, attrs) {

    private var areas: MutableList<DrawableArea>? = null
    private val paint: Paint = Paint()
    private val gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(getContext(), object : BaseGestureListener(){
            override fun onScale(rate: Float) {
                scale(rate)
            }
        })
    }

    private fun scale(rate: Float){
        val lp: ConstraintLayout.LayoutParams  = this.layoutParams as ConstraintLayout.LayoutParams
        val w = this.measuredWidth * rate
        val h = this.measuredHeight * rate
        lp.width = w.toInt()
        lp.height = h.toInt()
        this.layoutParams = lp
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

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