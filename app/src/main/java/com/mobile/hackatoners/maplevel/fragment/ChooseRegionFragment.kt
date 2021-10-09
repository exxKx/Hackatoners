package com.mobile.hackatoners.maplevel.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mobile.hackatoners.R
import com.mobile.hackatoners.maplevel.utils.Region
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
import android.view.animation.LinearInterpolator
import androidx.navigation.fragment.findNavController

class ChooseRegionFragment : Fragment(R.layout.fragment_choose_region) {

    private val dataHolder by lazy { DataHolder.getInstance(requireContext()) }

    private val backgroundOne by lazy { requireView().findViewById<View>(R.id.background_one) }
    private val backgroundTwo by lazy { requireView().findViewById<View>(R.id.background_two) }

    private val lock by lazy { requireView().findViewById<View>(R.id.lock) }

    private val regionForest by lazy { requireView().findViewById<View>(R.id.forest) }
    private val regionDesert by lazy { requireView().findViewById<View>(R.id.desert) }
    private val regionWorld by lazy { requireView().findViewById<View>(R.id.world) }
    private val regionHill by lazy { requireView().findViewById<View>(R.id.hill) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                AnimationUtils.loadAnimation(requireContext(), R.anim.float_animation)
            )
            delay(300)
            regionDesert.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.float_animation)
            )
            delay(300)
            regionWorld.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.float_animation)
            )
            delay(300)
            regionHill.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.float_animation)
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
    }

    private fun openDetailScreen(region: Region) {
        dataHolder.currentRegion = region.value
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