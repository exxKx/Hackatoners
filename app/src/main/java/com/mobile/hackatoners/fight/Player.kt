package com.mobile.hackatoners.fight

import android.content.Context
import com.mobile.hackatoners.R

class Player(context: Context, height: Float, widthScreen: Int) :
    GameObject(context, height, widthScreen) {

    init {
        this.setImageResource(R.drawable.green_walk_1)
        this.x = (41 * widthScreen / 100).toFloat()
        this.y = height
    }

    override fun run(toObject: GameObject) {
        if (this.x < toObject.x - toObject.width / 2)
            this.x += 5
    }

    override fun idle() {
        this.setImageResource(R.drawable.green_walk_1)
    }

    override fun attack(): Boolean {
        increaseAttackCount()
        when (imCount) {
            0 -> {
                this.setImageResource(R.drawable.attc_1)
                return false
            }
            1 -> {
                this.setImageResource(R.drawable.attc_2)
                return false
            }
            2 -> {
                this.setImageResource(R.drawable.attc_3)
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