package com.devsancho.slidebanner

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.devsancho.slidebanner.adapters.ViewPagerAdapter
import com.devsancho.slidebanner.interfaces.ItemClickListener
import com.devsancho.slidebanner.models.SlideModel
import com.devsancho.slidebanner.transformers.TransformersGroup
import java.util.*

class SlideBanner @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr) {

    private var viewPager: ViewPager? = null
    private var pagerDots: LinearLayout? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null

    private var dots: Array<ImageView?>? = null

    private var currentPage = 0
    private var imageCount = 0

    private var cornerRadius: Int = 0
    private var period: Long = 0
    private var delay: Long = 0
    private var autoCycle = false

    private var selectedDot = 0
    private var unselectedDot = 0
    private var errorImage = 0
    private var placeholder = 0
    private var indicatorSize: Int = 0
    private var defaultTransformer: Int = 0

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.image_slider, this, true)
        viewPager = findViewById(R.id.view_pager)
        pagerDots = findViewById(R.id.pager_dots)

        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.SlideBanner, defStyleAttr, defStyleAttr)
        indicatorSize = typedArray.getDimensionPixelSize(
            R.styleable.SlideBanner_indicatorSize,
            resources.getDimensionPixelSize(R.dimen.default_indicator_size)
        )
        cornerRadius = typedArray.getInt(R.styleable.SlideBanner_corner_radius, 0)
        period = typedArray.getInt(R.styleable.SlideBanner_period, 1000).toLong()
        delay = typedArray.getInt(R.styleable.SlideBanner_delay, 1000).toLong()
        autoCycle = typedArray.getBoolean(R.styleable.SlideBanner_auto_cycle, false)
        placeholder = typedArray.getResourceId(R.styleable.SlideBanner_placeholder, R.drawable.placeholder)
        errorImage = typedArray.getResourceId(R.styleable.SlideBanner_error_image, R.drawable.error)
        selectedDot =
            typedArray.getResourceId(R.styleable.SlideBanner_selected_dot, R.drawable.indicator_circle_selected)
        unselectedDot =
            typedArray.getResourceId(R.styleable.SlideBanner_unselected_dot, R.drawable.indicator_circle_unselected)
        defaultTransformer = typedArray.getInt(R.styleable.SlideBanner_defaultTransformer, TransformersGroup.SIMPLE)
    }

    fun setImageList(imageList: List<SlideModel>) {
        viewPagerAdapter = ViewPagerAdapter(context, imageList, cornerRadius, errorImage, placeholder)
        viewPager!!.adapter = viewPagerAdapter
        viewPager!!.setPageTransformer(false, TransformersGroup().getTransformer(context, defaultTransformer))
        imageCount = imageList.size
        setupDots(imageList.size)
        if (autoCycle) {
            autoSliding()
        }
    }

    fun setupDots(size: Int) {
        pagerDots!!.removeAllViews()
        dots = arrayOfNulls(size)

        for (i in 0 until size) {
            dots!![i] = ImageView(context)
            dots!![i]!!.setImageDrawable(ContextCompat.getDrawable(context, unselectedDot))
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            pagerDots!!.addView(dots!![i], params)
        }
        dots!![0]!!.setImageDrawable(ContextCompat.getDrawable(context, selectedDot))

        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                currentPage = position
                for (dot in dots!!) {
                    dot!!.setImageDrawable(ContextCompat.getDrawable(context, unselectedDot))
                }
                dots!![position]!!.setImageDrawable(ContextCompat.getDrawable(context, selectedDot))
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    fun autoSliding() {
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == imageCount) {
                currentPage = 0
            }
            viewPager!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, delay, period)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        viewPagerAdapter?.setItemClickListener(itemClickListener)
    }
}