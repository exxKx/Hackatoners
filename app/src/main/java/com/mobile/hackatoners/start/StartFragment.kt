package com.mobile.hackatoners.start

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.mobile.hackatoners.R

class StartFragment : Fragment(R.layout.fragment_start_welcome) {

    private val btn by lazy { requireView().findViewById<Button>(R.id.button_start) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn.setOnClickListener {
            (activity as StartActivity).switchToPerson()
        }
    }
}