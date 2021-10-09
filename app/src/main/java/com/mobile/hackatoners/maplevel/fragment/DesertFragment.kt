package com.mobile.hackatoners.maplevel.fragment

import android.animation.ValueAnimator
import android.graphics.BitmapFactory
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.mobile.hackatoners.R

class DesertFragment : Fragment(R.layout.fragment_desert) {

    private lateinit var backgroundOne: View
    private lateinit var backgroundTwo: View
    private lateinit var desert: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backgroundOne = view.findViewById(R.id.background_one)
        backgroundTwo = view.findViewById(R.id.background_two)
        desert = view.findViewById(R.id.desert)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_water_tile)
        val tiledImage = BitmapDrawable(resources, bitmap).also {
            it.setTileModeXY(
                Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT
            )
        }
        backgroundOne.background = tiledImage
        backgroundTwo.background = tiledImage

        ValueAnimator.ofFloat(0.0f, 1.0f).apply {
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            duration = 20000L
            addUpdateListener { animation ->
                val progress = animation.animatedValue as Float
                val width = backgroundOne.width.toFloat()
                val translationX = width * progress
                backgroundOne.translationX = translationX
                backgroundTwo.translationX = translationX - width
            }
        }.start()

        desert.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), R.anim.float_animation_up)
        )
    }
}