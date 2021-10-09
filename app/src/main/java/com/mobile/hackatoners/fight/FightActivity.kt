package com.mobile.hackatoners.fight

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import com.mobile.hackatoners.R
import kotlinx.android.synthetic.main.activity_fight.*
import android.animation.Animator

import android.animation.AnimatorListenerAdapter

import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.mobile.hackatoners.utils.Region

import com.yy.mobile.rollingtextview.CharOrder
import com.yy.mobile.rollingtextview.CharOrder.Alphabet
import com.yy.mobile.rollingtextview.CharOrder.Number
import com.yy.mobile.rollingtextview.strategy.Direction
import com.yy.mobile.rollingtextview.strategy.Strategy


class FightActivity : AppCompatActivity(), FightListener {

    val fightViewModel: FightViewModel by viewModels()
    
    private val region by lazy { Region.find(intent.getIntExtra(REGION, Region.FOREST.value)) }

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
        fight_scene.listenerFight = this
        rolling_text.animationDuration = 500L
        rolling_text.charStrategy = Strategy.CarryBitAnimation(Direction.SCROLL_DOWN)
        rolling_text.addCharOrder(Number)
        rolling_text.animationInterpolator = DecelerateInterpolator()
        rolling_text.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                //finsih
            }
        })
        yes_bttn.setOnClickListener {
            fight_scene.startTimer(true)
        }
        no_bttn.setOnClickListener {
            fight_scene.startTimer(false)
        }

        val playersLife = listOf(player_life_1, player_life_2, player_life_3, player_life_4)
        val bossLife = listOf(boss_life_1, boss_life_2, boss_life_3, boss_life_4, boss_life_5)
        fightViewModel.playerHP.observe(this) {
            Log.w("FightActivity", "playerHP $it")
            for (i in playersLife.indices) {
                if (i <= it - 1) {
                    playersLife[i].setImageResource(R.drawable.heart_live)
                } else {
                    playersLife[i].setImageResource(R.drawable.heart_dead)
                }

            }
        }

        fightViewModel.bossHP.observe(this) {
            Log.w("FightActivity", "bossHP $it")
            for (i in bossLife.indices) {
                if (i <= it - 1) {
                    bossLife[i].setImageResource(R.drawable.boss_life)
                } else {
                    bossLife[i].setImageResource(R.drawable.boss_life_gray)
                }

            }
        }
        fightViewModel.money.observe(this) {
            Log.w("FightActivity", "money $it")
        }

        when (region) { // TODO change background
            Region.FOREST -> Unit
            Region.DESERT -> Unit
            Region.WORLD -> Unit // never used
            Region.HILL -> Unit
        }
    }

    override fun updateLifeCount(player: Boolean) {
        if (player) {
            rolling_text.setText("19550")
            fightViewModel.playerAttack()
        } else {
            fightViewModel.bossAttack()
        }
    }

    companion object {
        const val REGION = "current_region"
    }
}