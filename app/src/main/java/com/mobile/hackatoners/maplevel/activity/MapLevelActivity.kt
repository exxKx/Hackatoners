package com.mobile.hackatoners.maplevel.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import com.mobile.hackatoners.R
import com.mobile.hackatoners.maplevel.utils.Region
import com.mobile.hackatoners.utils.DataHolder

class MapLevelActivity : AppCompatActivity() {

    private val dataHolder by lazy { DataHolder.getInstance(this) }

    private val regionHigh by lazy { findViewById<View>(R.id.high_region) }
    private val regionMiddle by lazy { findViewById<View>(R.id.middle_region) }
    private val regionLow by lazy { findViewById<View>(R.id.low_region) }

    private val playerHigh by lazy { findViewById<View>(R.id.player_high) }
    private val playerMiddle by lazy { findViewById<View>(R.id.player_middle) }
    private val playerLow by lazy { findViewById<View>(R.id.player_low) }

    private val actionNext by lazy { findViewById<AppCompatButton>(R.id.action_next) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maplevel)

        regionHigh.setOnClickListener {
            if (dataHolder.isHighRegionUnlocked) {
                dataHolder.currentRegion = Region.HIGH.value
                updatePlayerPosition()
            } else {
                showToast("Вы еще не открыли данную локацию")
            }
        }
        regionMiddle.setOnClickListener {
            if (dataHolder.isMiddleRegionUnlocked) {
                dataHolder.currentRegion = Region.MIDDLE.value
                updatePlayerPosition()
            } else {
                showToast("Вы еще не открыли данную локацию")
            }
        }
        regionLow.setOnClickListener {
            if (dataHolder.isLowRegionUnlocked) {
                dataHolder.currentRegion = Region.LOW.value
                updatePlayerPosition()
            } else {
                showToast("Вы еще не открыли данную локацию")
            }
        }
        actionNext.setOnClickListener {
            showToast("Открываем локацию ${Region.find(dataHolder.currentRegion).name}...")
        }
    }

    override fun onResume() {
        super.onResume()
        updatePlayerPosition()
    }

    private fun updatePlayerPosition() {
        Region.find(dataHolder.currentRegion).also { region ->
            playerHigh.isVisible = region == Region.HIGH
            playerMiddle.isVisible = region == Region.MIDDLE
            playerLow.isVisible = region == Region.LOW
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}