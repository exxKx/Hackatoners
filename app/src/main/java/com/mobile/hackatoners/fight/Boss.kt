package com.mobile.hackatoners.fight

import android.content.Context
import com.mobile.hackatoners.R

class Boss(context: Context, height: Float, widthScreen: Int) :
    GameObject(context, height, widthScreen) {

    init {
        this.setImageResource(R.drawable.red_walk_1)
        this.x = (47 * widthScreen / 100).toFloat()
        this.y = height
    }

    override fun idle() {
        this.setImageResource(R.drawable.red_walk_1)
    }

    override fun run(toObject: GameObject) {

    }

    override fun attack(): Boolean {
        increaseAttackCount()
        when (imCount) {
            0 -> {
                this.setImageResource(R.drawable.red_attc_1)
                return false
            }
            1 -> {
                this.setImageResource(R.drawable.red_attc_2)
                return false
            }
            2 -> {
                this.setImageResource(R.drawable.red_attc_3)
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