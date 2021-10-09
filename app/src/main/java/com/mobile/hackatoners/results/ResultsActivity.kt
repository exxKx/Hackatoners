package com.mobile.hackatoners.results

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobile.hackatoners.R

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
    }

    companion object {
        const val IS_VICTORY = "is_victory"
    }
}