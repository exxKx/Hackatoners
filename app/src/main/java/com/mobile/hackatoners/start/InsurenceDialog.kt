package com.mobile.hackatoners.start

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.button.MaterialButton
import com.mobile.hackatoners.R

class InsurenceDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialDialog(requireContext()).show {
            title(text = "ВТБ Страхование")
            message(res = R.string.insurence) {
                html()
            }
            customView(R.layout.dialog_welcome, horizontalPadding = true, scrollable = true)

            getCustomView().findViewById<MaterialButton>(R.id.action_alright)
                .setOnClickListener { dismiss() }
        }
    }
}