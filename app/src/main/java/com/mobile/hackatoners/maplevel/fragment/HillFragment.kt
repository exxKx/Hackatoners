package com.mobile.hackatoners.maplevel.fragment

import android.animation.ValueAnimator
import android.content.Intent
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
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.mobile.hackatoners.R
import com.mobile.hackatoners.fight.FightActivity
import com.mobile.hackatoners.utils.DataHolder
import com.mobile.hackatoners.utils.Region

class HillFragment : Fragment(R.layout.fragment_hill) {

    private val dataHolder by lazy { DataHolder.getInstance(requireContext()) }

    private lateinit var backgroundOne: View
    private lateinit var backgroundTwo: View
    private lateinit var hill: ImageView
    private lateinit var potions: TextView
    private lateinit var coins: TextView
    private lateinit var actionFight: MaterialButton
    private lateinit var actionBack: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backgroundOne = view.findViewById(R.id.background_one)
        backgroundTwo = view.findViewById(R.id.background_two)
        hill = view.findViewById(R.id.hill)
        potions = view.findViewById(R.id.potions)
        coins = view.findViewById(R.id.coins)
        actionFight = view.findViewById(R.id.action_fight)
        actionBack = view.findViewById(R.id.action_back)

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

        hill.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), R.anim.float_animation_up)
        )

        actionFight.setOnClickListener {
            if (dataHolder.potions > 0) {
                findNavController().navigate(R.id.potionDialog)
            } else {
                Intent(requireContext(), FightActivity::class.java).run {
                    putExtra(FightActivity.REGION, Region.HILL.value)
                    startActivity(this)
                }
            }
        }
        actionBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        val image = when (dataHolder.hillLevel) {
            0 -> R.drawable.bg_hill_1
            1 -> R.drawable.bg_hill_2
            2 -> R.drawable.bg_hill_3
            else -> R.drawable.bg_hill_4
        }
        hill.setImageResource(image)

        potions.text = getString(R.string.n_potions, dataHolder.potions)
        coins.text = getString(R.string.n_coins, dataHolder.coins)
    }
}