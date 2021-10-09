package com.mobile.hackatoners.fight

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mobile.hackatoners.R

class FightActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fight)
        val fight_scene = findViewById<FightView>(R.id.fight_scene)
        val yes_bttn = findViewById<Button>(R.id.yes)
        val no_bttn = findViewById<Button>(R.id.no)
        val windowManager = windowManager
        val display = windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        fight_scene.createPlayers(size.x)
        yes_bttn.setOnClickListener {
            fight_scene.startTimer(true)
        }
        no_bttn.setOnClickListener {
            fight_scene.startTimer(false)
        }
    }
}