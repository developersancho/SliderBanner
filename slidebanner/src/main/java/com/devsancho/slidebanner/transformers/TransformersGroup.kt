package com.devsancho.slidebanner.transformers

import android.content.Context
import androidx.viewpager.widget.ViewPager

class TransformersGroup {

    fun getTransformer(context: Context, transformer: Int): ViewPager.PageTransformer {
        return when (transformer) {
            ANTI_CLOCK_SPIN -> AntiClockSpinTransformer()
            CAROUSEL_EFFECT -> CarouselEffectTransformer(context)
            CLOCK_SPIN -> ClockSpinTransformer()
            CUBE_IN_ROTATION -> CubeInRotationTransformer()
            CUBE_OUT_ROTATION -> CubeOutRotationTransformer()
            DEPTH -> DepthTransformer()
            FADE_OUT -> FadeOutTransformer()
            HORIZONTAL_FLIP -> HorizontalFlipTransformer()
            SIMPLE -> SimpleTransformer()
            SPINNER -> SpinnerTransformer()
            VERTICAL_FLIP -> VerticalFlipTransformer()
            VERTICAL_SHUT -> VerticalShutTransformer()
            ZOOM_OUT -> ZoomOutTransformer()
            else -> ZoomOutTransformer()
        }
    }

    companion object {
        const val ANTI_CLOCK_SPIN = 1
        const val CAROUSEL_EFFECT = 2
        const val CLOCK_SPIN = 3
        const val CUBE_IN_ROTATION = 4
        const val CUBE_OUT_ROTATION = 5
        const val DEPTH = 6
        const val FADE_OUT = 7
        const val HORIZONTAL_FLIP = 8
        const val SIMPLE = 9
        const val SPINNER = 10
        const val VERTICAL_FLIP = 11
        const val VERTICAL_SHUT = 12
        const val ZOOM_OUT = 13
    }
}