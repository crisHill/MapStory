package com.zls.mapstory.listener

import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.sqrt

/**
 * @author criszhai
 * @date 2021/11/13 17:22
 * @desc
 */
abstract class BaseGestureListener: GestureDetector.OnGestureListener {

    private var lastCurrent = -1f

    override fun onDown(e: MotionEvent?): Boolean {
        lastCurrent = -1f
        return true
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        if (e2!!.pointerCount == 2) {
            val x = e2.getX(0) - e2.getX(1)
            val y = e2.getY(0) - e2.getX(1)
            val current = sqrt((x * x + y * y).toDouble()).toFloat()
            if (lastCurrent < 0) {
                lastCurrent = current
            } else {
                val gap = 20
                if (lastCurrent - current > gap) {
                    //两点距离变小
                    println("缩小")
                    lastCurrent = current
                    onScale(1.0f * (current + gap) / lastCurrent)
                } else if (current - lastCurrent > gap) {
                    println("放大")
                    lastCurrent = current
                    onScale(1.0f * (current - gap) / lastCurrent)
                }
            }
        }
        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    open fun onScale(rate: Float) {

    }
}