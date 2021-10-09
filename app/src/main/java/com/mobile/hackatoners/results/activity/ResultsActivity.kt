package com.mobile.hackatoners.results.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mobile.hackatoners.R

class ResultsActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        navController = supportFragmentManager
            .findFragmentById(R.id.nav_host)!!
            .findNavController()

        if (intent.getBooleanExtra(IS_VICTORY, true)) {
            navController.navigate(R.id.victoryFragment)
        } else {
            navController.navigate(R.id.victoryFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    companion object {
        const val IS_VICTORY = "is_victory"
    }
}