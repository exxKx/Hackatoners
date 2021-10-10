package com.mobile.hackatoners.fight

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import com.mobile.hackatoners.R
import kotlinx.android.synthetic.main.activity_fight.*
import android.animation.Animator

import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.view.View

import android.view.animation.DecelerateInterpolator
import androidx.core.view.ViewCompat.animate
import com.mobile.hackatoners.results.ResultsActivity
import com.mobile.hackatoners.results.ResultsActivity.Companion.ANSWERS_COUNT
import com.mobile.hackatoners.results.ResultsActivity.Companion.ELAPSED_TIME
import com.mobile.hackatoners.results.ResultsActivity.Companion.INFLATION
import com.mobile.hackatoners.results.ResultsActivity.Companion.IS_VICTORY
import com.mobile.hackatoners.results.ResultsActivity.Companion.RIGHT_ANSWERS
import com.mobile.hackatoners.start.openUrl
import com.mobile.hackatoners.utils.DataHolder
import com.mobile.hackatoners.utils.Region

import com.yy.mobile.rollingtextview.CharOrder.Number
import com.yy.mobile.rollingtextview.strategy.Direction
import com.yy.mobile.rollingtextview.strategy.Strategy
import kotlinx.android.synthetic.main.fragment_person.*
import kotlinx.coroutines.cancel


class FightActivity : AppCompatActivity(), FightListener {

    val fightViewModel: FightViewModel by viewModels()

    private val region by lazy { Region.find(intent.getIntExtra(REGION, Region.HILL.value)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fight)

        val fight_scene = findViewById<FightView>(R.id.fight_scene)
        val windowManager = windowManager
        val display = windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        fight_scene.createPlayers(size.x, size.y, fightViewModel.isGirl)
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
        option_1.setOnClickListener {
            if (fightViewModel.currentQuestion.value?.rightAnswers?.contains(1) == true)
                fight_scene.startTimer(true)
            else
                fight_scene.startTimer(false)
        }
        option_2.setOnClickListener {
            if (fightViewModel.currentQuestion.value?.rightAnswers?.contains(2) == true)
                fight_scene.startTimer(true)
            else
                fight_scene.startTimer(false)
        }
        option_3.setOnClickListener {
            if (fightViewModel.currentQuestion.value?.rightAnswers?.contains(3) == true)
                fight_scene.startTimer(true)
            else
                fight_scene.startTimer(false)
        }

        val playersLife = listOf(player_life_1, player_life_2, player_life_3, player_life_4)
        val bossLife = listOf(boss_life_1, boss_life_2, boss_life_3, boss_life_4, boss_life_5)
        fightViewModel.playerHP.observe(this) {
            Log.w("FightActivity", "playerHP $it")
            for (i in playersLife.indices) {
                fightViewModel.startHp?.let {
                    if (it < 4) {
                        playersLife[3].visibility = View.GONE
                    }
                    if (it < 3) {
                        playersLife[2].visibility = View.GONE
                    }
                    if (it < 2) {
                        playersLife[1].visibility = View.GONE
                    }
                }

                if (i <= it - 1) {
                    playersLife[i].setImageResource(R.drawable.heart_live)
                } else {
                    playersLife[i].setImageResource(R.drawable.heart_dead)
                }

            }
            if (it < 1)
                startResultActivity(victory = false)
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
            if (it < 1)
                startResultActivity(victory = true)
        }
        fightViewModel.money.observe(this) {
            rolling_text.setText(it.toString())
        }
        fightViewModel.currentState.observe(this) {
            it?.let {
                when (it) {
                    FightState.ONBOARD_1 -> {
                        unboard_1.visibility = View.VISIBLE
                        unboard_2.visibility = View.GONE
                        unboard_3.visibility = View.GONE
                    }
                    FightState.ONBOARD_2 -> {
                        unboard_1.visibility = View.GONE
                        unboard_2.visibility = View.VISIBLE
                        unboard_3.visibility = View.GONE
                    }
                    FightState.ONBOARD_3 -> {
                        unboard_1.visibility = View.GONE
                        unboard_2.visibility = View.GONE
                        unboard_3.visibility = View.VISIBLE
                    }
                    FightState.FIGHT -> {
                        unboard_1.visibility = View.GONE
                        unboard_2.visibility = View.GONE
                        unboard_3.visibility = View.GONE
                    }
                }
            }
        }

        unboard_1.setOnClickListener {
            fightViewModel.updateOnboarding()
        }
        unboard_2.setOnClickListener {
            fightViewModel.updateOnboarding()
        }
        unboard_3.setOnClickListener {
            fightViewModel.updateOnboarding()
        }
        fightViewModel.inflationEvent.observe(this) {
            animateShow()
        }

        when (region) {
            Region.FOREST -> fight_scene.setBackgroundResource(R.drawable.stage_forest)
            Region.DESERT -> fight_scene.setBackgroundResource(R.drawable.stage_desert)
            Region.WORLD -> Unit // never used
            Region.HILL -> fight_scene.setBackgroundResource(R.drawable.stage_hill)
        }

        fightViewModel.currentQuestion.observe(this) {
            question.text = getString(R.string.question, fightViewModel.answerCount + 1)
            question_title.text = getString(it.question)
            option_1.text = getString(it.answers[0])
            option_2.text = getString(it.answers[1])
            if (it.isThreeOptions) {
                option_3.text = getString(it.answers[2])
                option_3.visibility = View.VISIBLE
            } else {
                option_3.visibility = View.GONE
            }
        }
        what_is_it.setOnClickListener {
            this.openUrl("http://test-hackaton.tilda.ws/")
        }
    }

    private fun hideQuestionsView(view: View) {
        view.apply {
            alpha = 1f
            animate().alpha(0f).setDuration(220).setInterpolator(DecelerateInterpolator())
                .withEndAction {
                    fightViewModel.getRandomQuestion()
                    showQuestionsView(this)
                }.start()
        }
    }

    private fun showQuestionsView(view: View) {
        view.apply {
            alpha = 0f
            animate().alpha(1f).setDuration(220).setInterpolator(DecelerateInterpolator()).start()
        }
    }


    private fun animateShow() {
        inflation_text?.apply {
            this.visibility = View.VISIBLE
            alpha = 0f
            animate().alpha(1f).setDuration(1000).setInterpolator(DecelerateInterpolator())
                .withEndAction {
                    animateHide()
                }.start()

        }
    }

    private fun animateHide() {
        inflation_text?.apply {
            alpha = 1f
            animate().alpha(0f).setDuration(1000).setInterpolator(DecelerateInterpolator())
                .withEndAction {
                    this.visibility = View.GONE
                }.start()

        }
    }

    fun startResultActivity(victory: Boolean) {
        if (victory)
            fightViewModel.saveMoney()
        viewModelStore.clear()
        Intent(this, ResultsActivity::class.java).run {
            putExtra(REGION, region.value)
            putExtra(IS_VICTORY, victory)
            putExtra(ANSWERS_COUNT, fightViewModel.answerCount)
            putExtra(RIGHT_ANSWERS, fightViewModel.rightAnswerCount)
            putExtra(ELAPSED_TIME, fightViewModel.timeOnBoss)
            putExtra(INFLATION, fightViewModel.inflationSpent)
            startActivity(this)
        }
        finish()
    }


    override fun updateLifeCount(player: Boolean) {
        hideQuestionsView(quiest_container)
        if (player) {
            fightViewModel.playerAttack()
        } else {
            fightViewModel.bossAttack()
        }
    }

    companion object {
        const val REGION = "current_region"
    }
}