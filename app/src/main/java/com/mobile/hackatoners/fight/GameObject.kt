package com.mobile.hackatoners.fight

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatImageView

abstract class GameObject(context: Context, var height : Float,var widthScreen : Int): AppCompatImageView(context) {


    init {
        this.visibility = View.VISIBLE
        this.y = height
    }
    companion object{
        const val ANIMATE_COUNT = 4
    }

    protected var animateCount = 0
    protected var imCount = 0

    abstract fun run(toObject : GameObject)

    abstract fun attack() : Boolean

    abstract fun idle()

    abstract fun setGrounded(height : Float)

    protected fun increaseAttackCount() {
        if (animateCount < ANIMATE_COUNT) {
            animateCount++
        } else {
            animateCount = 0
        }
        if (animateCount == ANIMATE_COUNT) {
            if (imCount < 3) {
                imCount++
            } else {
                imCount = 0
            }
        }
    }


}