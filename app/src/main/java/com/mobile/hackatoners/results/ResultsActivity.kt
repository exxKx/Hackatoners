package com.mobile.hackatoners.results

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import com.google.android.material.button.MaterialButton
import com.mobile.hackatoners.R
import com.mobile.hackatoners.utils.Region

class ResultsActivity : AppCompatActivity() {

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

        when (Region.find(intent.getIntExtra(REGION, Region.HILL.value))) {
            Region.FOREST -> {
                background.setImageResource(R.drawable.stage_forest)
                scrollView.setBackgroundColor(
                    resources.getColor(R.color.colorForest)
                )
            }
            Region.DESERT -> {
                background.setImageResource(R.drawable.stage_desert)
                scrollView.setBackgroundColor(
                    resources.getColor(R.color.colorDesert)
                )
            }
            Region.WORLD -> Unit // never used
            Region.HILL -> {
                background.setImageResource(R.drawable.stage_hill)
                scrollView.setBackgroundColor(
                    resources.getColor(R.color.colorHill)
                )
            }
        }

        if (intent.getBooleanExtra(IS_VICTORY, true)) {
            actionShare.isVisible = true
            resultTitle.text = getString(R.string.victory)
            character.setImageResource(R.drawable.char_male_victory)
        } else {
            actionShare.isVisible = false
            resultTitle.text = getString(R.string.defeat)
            character.setImageResource(R.drawable.char_male_defeat)
        }

        rightAnswers.text = getString(
            R.string.n_answers,
            intent.getIntExtra(RIGHT_ANSWERS, 0),
            intent.getIntExtra(ANSWERS_COUNT, 0),
        )
        inflation.text = getString(
            R.string.n_inflation,
            intent.getIntExtra(INFLATION, 0)
        )
        elapsedTime.text = getString(
            R.string.n_elapsed_time,
            intent.getIntExtra(ELAPSED_TIME, 0)
        )

        actionShare.setOnClickListener {
            // todo open share intent
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