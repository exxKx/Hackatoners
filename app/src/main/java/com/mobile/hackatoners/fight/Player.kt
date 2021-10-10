package com.mobile.hackatoners.fight

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import com.mobile.hackatoners.R

class Player(context: Context, height: Float, widthScreen: Int, val girl: Boolean) :
    GameObject(context, height, widthScreen) {

    init {
        if (girl)
            this.setImageResource(R.drawable.woman_idle)
        else
            this.setImageResource(R.drawable.degault_man)
        this.x = (-1 * widthScreen / 10).toFloat()
    }


    override fun run(toObject: GameObject) {
        if (this.x < toObject.x - toObject.width / 2)
            this.x += 5
    }

    override fun idle() {
        if (girl)
            this.setImageResource(R.drawable.woman_idle)
        else
            this.setImageResource(R.drawable.degault_man)
    }

    override fun setGrounded(height: Float) {
        this.y = height / 2 - 256
    }

    override fun attack(): Boolean {
        increaseAttackCount()
        when (imCount) {
            0 -> {
                if (girl)
                    this.setImageResource(R.drawable.woman_attack_1)
                else
                    this.setImageResource(R.drawable.default_attack_1)
                return false
            }
            1 -> {
                if (girl)
                    this.setImageResource(R.drawable.woman_attack_2)
                else
                    this.setImageResource(R.drawable.default_attack_2)
                return false
            }
            2 -> {
                if (girl)
                    this.setImageResource(R.drawable.woman_attack_3)
                else
                    this.setImageResource(R.drawable.default_attack_3)
                return false
            }
            3 -> {
                if (animateCount == ANIMATE_COUNT) {
                    return true
                } else {
                    return false
                }
            }
            else -> {
                return false
            }
        }
    }

}