package com.mobile.hackatoners.start

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mobile.hackatoners.R
import com.mobile.hackatoners.common.DialogPresetBuilder
import com.mobile.hackatoners.common.makeDialog
import com.mobile.hackatoners.common.show
import com.mobile.hackatoners.maplevel.activity.MapLevelActivity
import kotlinx.android.synthetic.main.activity_fight.*
import kotlinx.android.synthetic.main.fragment_person.*

class PersonFragment : Fragment(R.layout.fragment_person) {

    val presenter = PersonPresenter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.init()

        person_play.setOnClickListener {
            activity?.startActivity(Intent(context, MapLevelActivity::class.java))
        }
        choose_1.setOnClickListener {
            presenter.chooseClicked(1)
        }
        choose_2.setOnClickListener {
            presenter.chooseClicked(2)
        }
        choose_3.setOnClickListener {
            presenter.chooseClicked(3)
        }
        choose_4.setOnClickListener {
            presenter.chooseClicked(4)
        }
        bonus_text.setOnClickListener {
            makeDialog(requireContext()) {
                title("ВТБ Страхование")
                body(
                    "«ВТБ Страхование» осуществляет деятельность по всем видам страхования юридических \n" +
                            "и физических лиц, включая обязательное медицинское страхование и страхование жизни, а также услуги перестрахования\n" +
                            "<a href='http://google.com'>Подробнее на сайте</a>"
                )
                positive("Понятно") { dismiss() }
            }.show()
        }


    }

    fun showQuestion(question: PersonPresenter.Question) {
        person_question.text = question.text

        showChooser(choose_1, question, 0)
        showChooser(choose_2, question, 1)
        showChooser(choose_3, question, 3)
        showChooser(choose_4, question, 3)
    }

    private fun showChooser(chooser: Button, question: PersonPresenter.Question, index: Int) {
        if (question.answers.getOrNull(index) != null) {
            chooser.text = question.answers[index]
            chooser.visibility = View.VISIBLE
        } else
            chooser.visibility = View.GONE
    }

    fun showResult() {
        bonus.show(true)
        person_question.visibility = View.GONE
        choose_1.visibility = View.GONE
        choose_2.visibility = View.GONE
        choose_3.visibility = View.GONE
        choose_4.visibility = View.GONE

        person_play.visibility = View.VISIBLE
    }
}