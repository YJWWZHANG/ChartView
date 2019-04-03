package com.zqb.chart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import java.util.*
import kotlin.collections.ArrayList

/**
 *创建时间:2019/4/1 14:17
 */
class BarChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mBaseWidth = 1080f
    private var mBaseHeight = 1920f

    private var mWidth = 0f
    private var mHeight = 0f

    private var mChartWidth = 0f
    private var mChartHeight = 0f

    private var mPercentX = 1f
    private var mPercentY = 1f

    private var mValueSpace = 0f
    private var mData = arrayListOf<Float>()
    private var mYAxisList = arrayListOf<Int>()
    private var mXAxisList = arrayListOf<String>()

    private var mXMaxTextRect = Rect()
    private var mYMaxTextRect = Rect()

    private var mStartX = 0f
    private var mStartY = 0f

    private var mXPaintText: Paint
    private var mYPaintText: Paint
    private var mTextSize = 25f

    private var mPaintLine: Paint
    private var mPaintBar: Paint

    private var mLineColor = Color.GRAY
    private var mXTextColor = Color.BLACK
    private var mYTextColor = Color.BLACK
    private var mBarStartColor = Color.RED
    private var mBarEndColor = Color.YELLOW
    private var mYAxisCount = 5

    private var mItem = 0f
    private var mItemSpace = 0f

    private var mIsShowXAxisText = true

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BarChartView)
        for (i in 0 until typedArray.indexCount) {
            val index = typedArray.getIndex(i)
            when (index) {
                R.styleable.BarChartView_lineColor -> {
                    mLineColor = typedArray.getColor(index, Color.BLACK)
                }
                R.styleable.BarChartView_xTextColor -> {
                    mXTextColor = typedArray.getColor(index, Color.BLACK)
                }
                R.styleable.BarChartView_yTextColor -> {
                    mYTextColor = typedArray.getColor(index, Color.BLACK)
                }
                R.styleable.BarChartView_barStartColor -> {
                    mBarStartColor = typedArray.getColor(index, Color.RED)
                }
                R.styleable.BarChartView_barEndColor -> {
                    mBarEndColor = typedArray.getColor(index, Color.YELLOW)
                }
                R.styleable.BarChartView_yAxisCount -> {
                    mYAxisCount = typedArray.getInt(index, 5)
                }
                R.styleable.BarChartView_showXAxis -> {
                    mIsShowXAxisText = typedArray.getBoolean(index, true)
                }
            }
        }
        typedArray.recycle()

        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            windowManager.defaultDisplay.getRealSize(point)
        } else {
            windowManager.defaultDisplay.getSize(point)
        }
        mPercentX = point.x / mBaseWidth
        mPercentY = point.y / mBaseHeight

        mXPaintText = Paint()
        mXPaintText.color = mXTextColor
        mXPaintText.textSize = mTextSize
        mXPaintText.isAntiAlias = true
        mXPaintText.strokeWidth = 1f

        mYPaintText = Paint()
        mYPaintText.color = mYTextColor
        mYPaintText.textSize = mTextSize
        mYPaintText.isAntiAlias = true
        mYPaintText.strokeWidth = 1f

        mPaintLine = Paint()
        mPaintLine.color = mLineColor
        mPaintLine.isAntiAlias = true
        mPaintLine.strokeWidth = 2f

        mPaintBar = Paint()
        mPaintBar.style = Paint.Style.FILL
        mPaintBar.color = Color.RED
        mPaintBar.strokeWidth = 4f
        mPaintBar.maskFilter = BlurMaskFilter(1f, BlurMaskFilter.Blur.INNER)

        mXAxisList.addAll(arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"))
        mData.addAll(arrayListOf(100f, 200f, 300f, 400f, 500f, 600f, 700f, 800f, 900f, 1000f, 1100f, 1200f))
    }

    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t, r, b)
        val maxData = Collections.max(mData)
        val v = maxData % mYAxisCount
        mValueSpace = if (v == 0f) {
            maxData / mYAxisCount
        } else {
            (maxData + (mYAxisCount - v)) / mYAxisCount
        }
        mYAxisList.clear()
        for (i in 0..mYAxisCount) {
            mYAxisList.add(i * mValueSpace.toInt())
        }
        val maxYAxis = Collections.max(mYAxisList)

        mXPaintText.getTextBounds(mXAxisList[mXAxisList.size - 1], 0, mXAxisList[mXAxisList.size - 1].length, mXMaxTextRect)
        mYPaintText.getTextBounds(maxYAxis.toString(), 0, maxYAxis.toString().length, mYMaxTextRect)

        mWidth = width * mPercentX
        mHeight = height * mPercentY

        mStartX = mYMaxTextRect.width() + mYMaxTextRect.height() * mPercentX
        if (!mIsShowXAxisText) {
            mStartY = mHeight - mYMaxTextRect.height()
            mChartHeight = mHeight - mYMaxTextRect.height() * 2
        } else {
            mStartY = mHeight - mYMaxTextRect.height() / 2 - mXMaxTextRect.height() - mYMaxTextRect.height()
            mChartHeight = mHeight - mYMaxTextRect.height() - mYMaxTextRect.height() / 2 - mXMaxTextRect.height() - mYMaxTextRect.height()
        }

        mChartWidth = mWidth - mStartX - mYMaxTextRect.height() * mPercentX

        mItem = mChartWidth / mData.size * 0.7f
        mItemSpace = mChartWidth / mData.size * 0.15f

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0..mData.size) {
            canvas.drawLine(
                mStartX + i * mChartWidth / mData.size,
                mYMaxTextRect.height().toFloat(),
                mStartX + i * mChartWidth / mData.size,
                mStartY,
                mPaintLine
            )
        }
        if (mIsShowXAxisText) {
//            val textRect = Rect()
//            mXPaintText.getTextBounds(mXAxisList[0], 0, mXAxisList[0].length, textRect)
//            canvas.drawText(mXAxisList[0], mStartX + (mChartWidth / mData.size - textRect.width()) / 2, mStartY + mXMaxTextRect.height() + mYMaxTextRect.height() / 2, mXPaintText)
            for (i in 0 until mXAxisList.size) {
                val textRect = Rect()
                mXPaintText.getTextBounds(mXAxisList[i], 0, mXAxisList[i].length, textRect)
                canvas.drawText(mXAxisList[i], mStartX + (mChartWidth / mData.size - textRect.width()) / 2 + i * (mChartWidth / mData.size), mStartY + mXMaxTextRect.height() + mYMaxTextRect.height() / 2, mXPaintText)
            }
        }
        for (i in 0..mYAxisCount) {
            canvas.drawLine(
                mStartX,
                mYMaxTextRect.height().toFloat() + i * mChartHeight / mYAxisCount,
                mWidth - mYMaxTextRect.height() * mPercentX,
                mYMaxTextRect.height().toFloat() + i * mChartHeight / mYAxisCount,
                mPaintLine
            )
            val textRect = Rect()
            mYPaintText.getTextBounds(
                mYAxisList[mYAxisCount - i].toString(),
                0,
                mYAxisList[mYAxisCount - i].toString().length,
                textRect
            )
            canvas.drawText(
                mYAxisList[mYAxisCount - i].toString(),
                mStartX - textRect.width() - mYMaxTextRect.height() / 2 * mPercentX,
                mYMaxTextRect.height().toFloat() + i * mChartHeight / mYAxisCount + textRect.height() / 2,
                mYPaintText
            )
        }
        for (i in 0 until mData.size) {
            mPaintBar.shader = LinearGradient(
                mStartX + mItemSpace + i * (mChartWidth / mData.size),
                mStartY - mChartHeight * (mData[i] / mYAxisList[mYAxisList.size - 1]),
                mStartX + mItemSpace + mItem + i * (mChartWidth / mData.size),
                mStartY, mBarStartColor, mBarEndColor, Shader.TileMode.MIRROR
            )
            canvas.drawRect(
                mStartX + mItemSpace + i * (mChartWidth / mData.size),
                mStartY - mChartHeight * (mData[i] / mYAxisList[mYAxisList.size - 1]),
                mStartX + mItemSpace + mItem + i * (mChartWidth / mData.size),
                mStartY,
                mPaintBar
            )
        }
    }

    fun setLineColor(lineColor: Int) {
        mLineColor = lineColor
    }

    fun setYTextColor(yTextColor: Int) {
        mYTextColor = yTextColor
    }

    fun setBarColor(startColor: Int, endColor: Int) {
        mBarStartColor = startColor
        mBarEndColor = endColor
    }

    fun setYAxisCount(yAxisCount: Int) {
        mYAxisCount = yAxisCount
    }

    fun setShowXAxis(isShow: Boolean) {
        mIsShowXAxisText = isShow
    }

    fun setXAxisData(data: ArrayList<String>) {
        mXAxisList = data
        invalidate()
    }

    fun setBarData(data: ArrayList<Float>) {
        mData = data
        invalidate()
    }
}