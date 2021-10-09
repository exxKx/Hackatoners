package com.mobile.hackatoners.maplevel.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mobile.hackatoners.R
import com.mobile.hackatoners.utils.Region
import com.mobile.hackatoners.utils.DataHolder
import android.graphics.drawable.BitmapDrawable
import android.graphics.BitmapFactory
import android.graphics.Shader
import android.view.animation.AnimationUtils
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController

class ChooseRegionFragment : Fragment(R.layout.fragment_choose_region) {

    private val dataHolder by lazy { DataHolder.getInstance(requireContext()) }

    private lateinit var backgroundOne: View
    private lateinit var backgroundTwo: View
    private lateinit var regionForest: ImageView
    private lateinit var regionDesert: ImageView
    private lateinit var regionWorld: ImageView
    private lateinit var regionHill: ImageView
    private lateinit var forestText: TextView
    private lateinit var desertText: TextView
    private lateinit var hillText: TextView
    private lateinit var lock: ImageView
    private lateinit var potions: TextView
    private lateinit var coins: TextView
    private lateinit var percents: TextView

    @SuppressLint("StringFormatInvalid")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backgroundOne = view.findViewById(R.id.background_one)
        backgroundTwo = view.findViewById(R.id.background_two)
        regionForest = view.findViewById(R.id.forest)
        regionDesert = view.findViewById(R.id.desert)
        regionWorld = view.findViewById(R.id.world)
        regionHill = view.findViewById(R.id.hill)
        forestText = view.findViewById(R.id.forest_text)
        desertText = view.findViewById(R.id.desert_text)
        hillText = view.findViewById(R.id.hill_text)
        lock = view.findViewById(R.id.lock)
        potions = view.findViewById(R.id.potions)
        coins = view.findViewById(R.id.coins)
        percents = view.findViewById(R.id.percents)

        if (!dataHolder.isMapTutorialComplete) {
            findNavController().navigate(R.id.welcomeDialog)
            dataHolder.isMapTutorialComplete = true
        }

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

        lock.isVisible = !dataHolder.isRealWorldUnlocked

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            regionForest.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.float_animation_down)
            )
            delay(300)
            regionDesert.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.float_animation_down)
            )
            delay(300)
            regionWorld.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.float_animation_down)
            )
            delay(300)
            regionHill.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.float_animation_down)
            )
        }

        regionForest.setOnClickListener {
            openDetailScreen(Region.FOREST)
        }
        regionDesert.setOnClickListener {
            openDetailScreen(Region.DESERT)
        }
        regionWorld.setOnClickListener {
            if (dataHolder.isRealWorldUnlocked) {
                openDetailScreen(Region.HILL)
            } else {
                showToast(R.string.complete_other_first)
            }
        }
        regionHill.setOnClickListener {
            openDetailScreen(Region.HILL)
        }

        potions.text = getString(R.string.n_potions, dataHolder.potions)
        coins.text = getString(R.string.n_coins, dataHolder.coins)

        forestText.text = getString(R.string.forest_level, dataHolder.forestLevel)
        desertText.text = getString(R.string.desert_level, dataHolder.desertLevel)
        hillText.text = getString(R.string.hill_level, dataHolder.hillLevel)

        val forestPercents = 100 * dataHolder.forestLevel / 3
        val desertPercents = 100 * dataHolder.desertLevel / 3
        val hillPercents = 100 * dataHolder.hillLevel / 3

        percents.text = getString(
            R.string.n_percents,
            ((forestPercents + desertPercents + hillPercents) / 3)
        )
    }

    private fun openDetailScreen(region: Region) {
        val id = when (region) {
            Region.FOREST -> R.id.leftFragment
            Region.DESERT -> R.id.rightFragment
            Region.WORLD -> R.id.middleFragment
            Region.HILL -> R.id.lowFragment
        }
        findNavController().navigate(id)
    }

    private fun showToast(@StringRes message: Int) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}