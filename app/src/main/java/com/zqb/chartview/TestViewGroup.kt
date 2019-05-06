package com.zqb.chartview

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent

/**
 *创建时间:2019/5/6 16:52
 */
class TestViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        return true
//        return false
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
//        return true
        return false
//        return super.onInterceptTouchEvent(ev)

//        val x = ev.getX()
//        val y = ev.getY()
//
//        val action = ev.getAction()
//        when (action) {
//            MotionEvent.ACTION_DOWN -> {
//                mDownPosX = x
//                mDownPosY = y
//            }
//            MotionEvent.ACTION_MOVE -> {
//                val deltaX = Math.abs(x - mDownPosX)
//                val deltaY = Math.abs(y - mDownPosY)
//                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
//                if (deltaX > deltaY) {
//                    return false
//                }
//            }
//        }
//
//        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e("onTouchEvent", "TestViewGroup onTouchEvent")
        return true
//        return super.onTouchEvent(event)
    }
}