package com.mobile.hackatoners.start

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobile.hackatoners.R

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, StartFragment())
            .commit()
    }

    fun switchToPerson() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PersonFragment())
            .commit()
    }
}