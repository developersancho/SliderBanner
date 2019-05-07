package com.devsancho.slidebanner.transformers

import android.view.View
import androidx.viewpager.widget.ViewPager

class FadeOutTransformer : ViewPager.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        page.translationX = -position * page.width
        page.alpha = 1 - Math.abs(position)
    }

}