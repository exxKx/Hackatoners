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
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mobile.hackatoners.R
import com.mobile.hackatoners.utils.DataHolder

class ForestFragment : Fragment(R.layout.fragment_forest) {

    private val dataHolder by lazy { DataHolder.getInstance(requireContext()) }

    private lateinit var backgroundOne: View
    private lateinit var backgroundTwo: View
    private lateinit var forest: ImageView
    private lateinit var potions: TextView
    private lateinit var coins: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backgroundOne = view.findViewById(R.id.background_one)
        backgroundTwo = view.findViewById(R.id.background_two)
        forest = view.findViewById(R.id.forest)
        potions = view.findViewById(R.id.potions)
        coins = view.findViewById(R.id.coins)

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

        forest.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), R.anim.float_animation_up)
        )

        val image = when (dataHolder.hillLevel) {
            1 -> R.drawable.bg_forest_1
            2 -> R.drawable.bg_forest_2
            3 -> R.drawable.bg_forest_3
            else -> R.drawable.bg_forest_4
        }
        forest.setImageResource(image)

        potions.text = getString(R.string.n_potions, dataHolder.potions)
        coins.text = getString(R.string.n_coins, dataHolder.coins)
    }
}