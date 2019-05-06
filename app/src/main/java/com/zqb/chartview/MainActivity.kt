package com.zqb.chartview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        bar_chart_view.setBarData(arrayListOf(800f, 1600f, 300f, 400f, 1000f, 100f, 800f, 1600f, 300f, 400f, 1000f, 2500f, 250f, 280f, 2050f))
//        bar_chart_view.setXAxisData(arrayListOf("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月", "13月", "14月", "15月"))
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        return true
//        return false
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e("onTouchEvent", "MainActivity onTouchEvent")
//        return true
//        return false
        return super.onTouchEvent(event)
    }
}
