package com.mobile.hackatoners.start

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.graphics.Path
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import com.mobile.hackatoners.R
import kotlinx.android.synthetic.main.fragment_start_welcome.*

class StartFragment : Fragment(R.layout.fragment_start_welcome) {

    private val btn by lazy { requireView().findViewById<Button>(R.id.button_start) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        anim(splash_cloud_bg, false)

        btn.setOnClickListener {
            (activity as StartActivity).switchToPerson()
        }
    }

    private fun anim(view: View, reverse : Boolean) {
        val path = Path().apply {
            addCircle(0f, 0f, 10f, if (reverse) Path.Direction.CCW else Path.Direction.CW)
        }
        ObjectAnimator.ofFloat(view, View.X, View.Y, path).apply {
            duration = 3500
            start()
            this.doOnEnd {
                anim(view, !reverse)
            }
        }
    }
}