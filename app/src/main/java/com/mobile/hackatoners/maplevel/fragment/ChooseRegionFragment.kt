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

class ChooseRegionFragment : Fragment(R.layout.fragment_choose_region) {

    private val dataHolder by lazy { DataHolder.getInstance(requireContext()) }

    private val backgroundOne by lazy { requireView().findViewById<View>(R.id.background_one) }
    private val backgroundTwo by lazy { requireView().findViewById<View>(R.id.background_two) }

    private val regionLeft by lazy { requireView().findViewById<View>(R.id.left_region) }
    private val regionRight by lazy { requireView().findViewById<View>(R.id.right_region) }
    private val regionMiddle by lazy { requireView().findViewById<View>(R.id.middle_region) }
    private val regionLow by lazy { requireView().findViewById<View>(R.id.low_region) }

    private val lock by lazy { requireView().findViewById<View>(R.id.lock) }

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
            duration = 10000L
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
            regionLeft.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.float_animation)
            )
            delay(300)
            regionRight.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.float_animation)
            )
            delay(300)
            regionMiddle.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.float_animation)
            )
            delay(300)
            regionLow.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.float_animation)
            )
        }

        regionLeft.setOnClickListener {
            openDetailScreen(Region.LEFT)
        }
        regionRight.setOnClickListener {
            openDetailScreen(Region.RIGHT)
        }
        regionMiddle.setOnClickListener {
            if (dataHolder.isRealWorldUnlocked) {
                openDetailScreen(Region.LOW)
            } else {
                showToast(R.string.complete_other_first)
            }
        }
        regionLow.setOnClickListener {
            openDetailScreen(Region.LOW)
        }
    }

    private fun openDetailScreen(region: Region) {
        dataHolder.currentRegion = region.value
        // open screen
    }

    private fun showToast(@StringRes message: Int) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}