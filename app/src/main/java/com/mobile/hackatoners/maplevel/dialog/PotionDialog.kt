package com.mobile.hackatoners.maplevel.dialog

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.button.MaterialButton
import com.mobile.hackatoners.R
import com.mobile.hackatoners.fight.FightActivity
import com.mobile.hackatoners.utils.Region

class PotionDialog : DialogFragment() {

    private val region by lazy {
        Region.find(arguments?.getInt(FightActivity.REGION) ?: Region.HILL.value)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialDialog(requireContext()).show {
            title(R.string.are_you_ready_to_fight)
            message(R.string.you_can_use_potion) {
                html()
            }
            customView(R.layout.dialog_potion, horizontalPadding = true, scrollable = true)

            getCustomView().findViewById<MaterialButton>(R.id.action_use_potion)
                .setOnClickListener {
                    // TODO use 1 potion
                    Intent(requireContext(), FightActivity::class.java).run {
                        putExtra(FightActivity.REGION, region.value)
                        startActivity(this)
                    }
                    dismiss()
                }
            getCustomView().findViewById<MaterialButton>(R.id.action_go_fight)
                .setOnClickListener {
                    Intent(requireContext(), FightActivity::class.java).run {
                        putExtra(FightActivity.REGION, region.value)
                        startActivity(this)
                    }
                    dismiss()
                }
        }
    }
}