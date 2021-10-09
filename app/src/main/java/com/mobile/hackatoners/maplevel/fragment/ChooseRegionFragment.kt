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

class ChooseRegionFragment : Fragment(R.layout.fragment_choose_region) {

    private val dataHolder by lazy { DataHolder.getInstance(requireContext()) }

    private val regionLeft by lazy { requireView().findViewById<View>(R.id.left_region) }
    private val regionRight by lazy { requireView().findViewById<View>(R.id.right_region) }
    private val regionMiddle by lazy { requireView().findViewById<View>(R.id.middle_region) }
    private val regionLow by lazy { requireView().findViewById<View>(R.id.low_region) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_water_tile)
        view.background = BitmapDrawable(resources, bitmap).also {
            it.setTileModeXY(
                Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT
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
                showToast("Для начала пройдите остальные уровни")
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

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}