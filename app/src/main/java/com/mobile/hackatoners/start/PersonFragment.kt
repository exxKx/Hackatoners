package com.mobile.hackatoners.start

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mobile.hackatoners.R
import com.mobile.hackatoners.common.show
import com.mobile.hackatoners.maplevel.activity.MapLevelActivity
import com.mobile.hackatoners.utils.DataHolder
import kotlinx.android.synthetic.main.fragment_person.*

class PersonFragment : Fragment(R.layout.fragment_person) {

    lateinit var presenter : PersonPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = PersonPresenter(this, DataHolder.getInstance(requireContext()))

        super.onViewCreated(view, savedInstanceState)

        presenter.init()

        person_what_is_it.setOnClickListener {
            context?.openUrl("http://test-hackaton.tilda.ws/")
        }
        person_play.setOnClickListener {
            activity?.startActivity(Intent(context, MapLevelActivity::class.java))
            requireActivity().finish()
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
        bonus.setOnClickListener {
            InsurenceDialog().show(
                requireFragmentManager(), "bonusDialog"
            )
        }


    }

    fun showQuestion(question: PersonPresenter.Question) {
        person_question.text = question.text

        showChooser(choose_1, question, 0)
        showChooser(choose_2, question, 1)
        showChooser(choose_3, question, 2)
        showChooser(choose_4, question, 3)
    }

    private fun showChooser(chooser: TextView, question: PersonPresenter.Question, index: Int) {
        if (question.answers.getOrNull(index) != null) {
            chooser.text = question.answers[index]
            chooser.visibility = View.VISIBLE
        } else
            chooser.visibility = View.GONE
    }

    fun showResult(girl: Boolean) {
        skillet.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), if (girl) R.drawable.char_2_fun else R.drawable.char_1_fun)
        )
        person_play_text.show(true)
        bonus.show(true)
        person_question.visibility = View.GONE
        choose_1.visibility = View.GONE
        choose_2.visibility = View.GONE
        choose_3.visibility = View.GONE
        choose_4.visibility = View.GONE

        person_play.visibility = View.VISIBLE
    }

    fun showGirl() {
        skillet.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.char_2_2)
        )
    }

    fun showMan() {
        skillet.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.char_1_1)
        )
    }


    fun showHp(hp: Int) {
        val hearts = listOf<View>(heart_1, heart_2, heart_3, heart_4, heart_5)

        hearts.forEachIndexed { index, view ->
            view.show(index < hp)
        }
    }

    fun showCoins(coins: Int) {
        coins_view.show()
        coins_view.text = "$coins ₽"
    }
}

fun Context.openUrl(url: String, browserChooser: Boolean = false) {
    try {
        if (browserChooser) {
            Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
        } else {
            Intent(Intent.ACTION_VIEW)
        }
            .apply { data = Uri.parse(url) }
            .also(::startActivity)
    } catch (e: ActivityNotFoundException) {
        }
    }