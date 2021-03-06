package com.mobile.hackatoners.fight

import android.content.Context
import com.mobile.hackatoners.R

class Boss(context: Context, height: Float, widthScreen: Int) :
    GameObject(context, height, widthScreen) {

    init {
        this.setImageResource(R.drawable.monster_idle)
        this.x = (55 * widthScreen / 100).toFloat()
    }

    override fun idle() {
        this.setImageResource(R.drawable.monster_idle)
    }

    override fun setGrounded(height: Float) {
        this.y = height / 2 - 156f
    }

    override fun run(toObject: GameObject) {

    }

    override fun attack(): Boolean {
        increaseAttackCount()
        when (imCount) {
            0 -> {
                this.setImageResource(R.drawable.monster_attack_1)
                return false
            }
            1 -> {
                this.setImageResource(R.drawable.monster_attack_2)
                return false
            }
            2 -> {
                this.setImageResource(R.drawable.monster_attack_3)
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