package com.mobile.hackatoners.maplevel.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.mobile.hackatoners.R
import com.mobile.hackatoners.maplevel.utils.Region
import com.mobile.hackatoners.utils.DataHolder

class MapLevelActivity : AppCompatActivity() {

    private val dataHolder by lazy { DataHolder.getInstance(this) }

    private val playerHigh by lazy { findViewById<View>(R.id.player_high) }
    private val playerMiddle by lazy { findViewById<View>(R.id.player_middle) }
    private val playerLow by lazy { findViewById<View>(R.id.player_low) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maplevel)

        Region.find(dataHolder.region).also { region ->
            playerHigh.isVisible = region == Region.HIGH
            playerMiddle.isVisible = region == Region.MIDDLE
            playerLow.isVisible = region == Region.LOW
        }
    }
}