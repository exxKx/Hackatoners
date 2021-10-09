package com.mobile.hackatoners.maplevel.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobile.hackatoners.R
import com.mobile.hackatoners.maplevel.fragment.ChooseRegionFragment

class MapLevelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maplevel)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ChooseRegionFragment())
            .commit()
    }
}