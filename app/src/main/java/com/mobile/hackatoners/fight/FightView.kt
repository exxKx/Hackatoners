package com.mobile.hackatoners.fight

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*

class FightView : ConstraintLayout {

    private var sizeX: Float = 0.toFloat()
    private var sizeY: Float = 0.toFloat()
    private var player: GameObject? = null
    private var boss: GameObject? = null
    private var timer: Timer? = null
    private var idle = true
    var listenerFight: FightListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {

    }

    private fun playerAttack() {
        if (player?.attack() == true) {
            player?.idle()
            timer?.cancel()
            timer = null
            listenerFight?.updateLifeCount(true)
            idle = true
        }
    }

    private fun bossAttack() {
        if (boss?.attack() == true) {
            boss?.idle()
            timer?.cancel()
            timer = null
            listenerFight?.updateLifeCount(false)
            idle = true
        }
    }


    fun createPlayers(x: Int) {
        player = Player(context, 700f, x)
        boss = Boss(context, 700f, x)
        this.addView(player)
        this.addView(boss)
    }

    fun startTimer(player: Boolean) {
        if (idle) {
            idle = false
            if (timer == null)
                timer = Timer()
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    handler.post(Runnable {
                        if (player)
                            playerAttack()
                        else
                            bossAttack()
                    })
                }
            }, 0, 20)
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        sizeX = getWidth().toFloat()
        sizeY = getHeight().toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        //super.onDraw(canvas)
        //player?.draw(canvas)
    }
}