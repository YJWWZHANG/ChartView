package com.zqb.chartview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 *创建时间:2019/5/6 16:25
 */
class TestView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        val size = MeasureSpec.getSize(heightMeasureSpec)
        var result = 500
        if (mode == MeasureSpec.AT_MOST) {
//            result = size
        } else if (mode == MeasureSpec.EXACTLY) {
            result = size
        }
        return result
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        val size = MeasureSpec.getSize(widthMeasureSpec)
        var result = 500
        if (mode == MeasureSpec.AT_MOST) {
//            result = size
        } else if (mode == MeasureSpec.EXACTLY) {
            result = size
        }
        return result
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
//        return true
//        return false
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e("onTouchEvent", "TestView onTouchEvent")
        return true
//        return super.onTouchEvent(event)
    }

}