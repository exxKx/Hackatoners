package com.mobile.hackatoners.results

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import com.google.android.material.button.MaterialButton
import com.mobile.hackatoners.R
import com.mobile.hackatoners.utils.DataHolder
import com.mobile.hackatoners.utils.Region
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.layout_card_semi_victory.*
import kotlinx.android.synthetic.main.layout_card_victory.*
import kotlinx.android.synthetic.main.layout_card_victory.action_buy
import kotlinx.android.synthetic.main.layout_card_victory.action_continue

class ResultsActivity : AppCompatActivity() {

    private val dataHolder by lazy { DataHolder.getInstance(this) }

    private lateinit var background: ImageView // Фон персонажа
    private lateinit var character: ImageView // Персонаж
    private lateinit var resultTitle: TextView // Победа! или Поражение!
    private lateinit var actionShare: MaterialButton // Рассказать об успехах
    private lateinit var scrollView: NestedScrollView // Фон скролла
    private lateinit var rightAnswers: TextView // Правильных ответов (результат)
    private lateinit var inflation: TextView // Инфляция (результат)
    private lateinit var elapsedTime: TextView // Время боя (результат)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        background = findViewById(R.id.background)
        character = findViewById(R.id.character)
        resultTitle = findViewById(R.id.title)
        actionShare = findViewById(R.id.action_share)
        scrollView = findViewById(R.id.results_scroll)
        rightAnswers = findViewById(R.id.right_answers_value)
        inflation = findViewById(R.id.inflation_value)
        elapsedTime = findViewById(R.id.elapsed_time_value)

        val isVictory = intent.getBooleanExtra(IS_VICTORY, true)
        when (Region.find(intent.getIntExtra(REGION, Region.HILL.value))) {
            Region.FOREST -> {
                if (isVictory) {
                    dataHolder.forestLevel += 1
                }
                background.setImageResource(R.drawable.stage_forest)
                scrollView.setBackgroundColor(
                    resources.getColor(R.color.colorForest)
                )
            }
            Region.DESERT -> {
                if (isVictory) {
                    dataHolder.desertLevel += 1
                }
                background.setImageResource(R.drawable.stage_desert)
                scrollView.setBackgroundColor(
                    resources.getColor(R.color.colorDesert)
                )
            }
            Region.WORLD -> Unit // never used
            Region.HILL -> {
                if (isVictory) {
                    dataHolder.hillLevel += 1
                }
                background.setImageResource(R.drawable.stage_hill)
                scrollView.setBackgroundColor(
                    resources.getColor(R.color.colorHill)
                )
            }
        }

        val rightAnswersCount = intent.getIntExtra(RIGHT_ANSWERS, 0)
        val allAnswersCount = intent.getIntExtra(ANSWERS_COUNT, 0)
        rightAnswers.text = getString(
            R.string.n_answers,
            rightAnswersCount,
            allAnswersCount,
        )
        inflation.text = getString(
            R.string.n_inflation,
            intent.getIntExtra(INFLATION, 0)
        )
        elapsedTime.text = getString(
            R.string.n_elapsed_time,
            intent.getIntExtra(ELAPSED_TIME, 0)
        )

        if (isVictory) {
            actionShare.isVisible = true
            resultTitle.text = getString(R.string.victory)
            character.setImageResource(R.drawable.char_male_victory)
            if (rightAnswersCount >= allAnswersCount) {
                LayoutInflater.from(this)
                    .inflate(R.layout.layout_card_victory, card_view)
                action_buy.setOnClickListener {
                    startVtbInvest()
                }
                action_continue.setOnClickListener {
                    finish()
                }
            } else {
                LayoutInflater.from(this)
                    .inflate(R.layout.layout_card_semi_victory, card_view)

                desc2.text = "blyat!! nado uspet"

                group.referencedIds.forEach {
                    findViewById<View>(it).setOnClickListener {
                        // TODO open school url
                    }
                }
                action_buy.setOnClickListener {
                    startVtbInvest()
                }
                action_continue.setOnClickListener {
                    finish()
                }
            }
        } else {
            actionShare.isVisible = false
            resultTitle.text = getString(R.string.defeat)
            character.setImageResource(R.drawable.char_male_defeat)
            LayoutInflater.from(this)
                .inflate(R.layout.layout_card_victory, card_view) // todo defeat
        }

        actionShare.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun startVtbInvest() {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=ru.vtb.invest")
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=ru.vtb.invest")
                )
            )
        }
    }

    companion object {
        const val REGION = "current_region" // Value from Region class
        const val IS_VICTORY = "is_victory" // Boolean
        const val ANSWERS_COUNT = "answers_count" // Int, All answers (including false ones)
        const val RIGHT_ANSWERS = "right_answers" // Int, Right answers count
        const val INFLATION = "inflation" // Int, inflation in rubles
        const val ELAPSED_TIME = "elapsed_time" // Int, seconds taken for level
    }
}