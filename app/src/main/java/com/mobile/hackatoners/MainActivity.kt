package com.mobile.hackatoners

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobile.hackatoners.maplevel.activity.MapLevelActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        finish() // todo remove
        startActivity(Intent(this, MapLevelActivity::class.java))
    }
}