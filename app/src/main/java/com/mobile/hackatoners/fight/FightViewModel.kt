package com.mobile.hackatoners.fight

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.hackatoners.utils.DataHolder

class FightViewModel(app: Application) : AndroidViewModel(app) {

    private val dataHolder by lazy { DataHolder.getInstance(app.applicationContext) }

    val playerHP: MutableLiveData<Int> = MutableLiveData(4)
    val bossHP: MutableLiveData<Int> = MutableLiveData(5)
    val money: MutableLiveData<Int> = MutableLiveData(20000)
    val currentState: MutableLiveData<FightState> = MutableLiveData(FightState.FIGHT)

    init {
        if (!dataHolder.isFightOnboardingComplete)
            startOnboarding()
    }


    fun bossAttack() {
        playerHP.value = playerHP.value?.minus(1)
    }

    fun playerAttack() {
        bossHP.value = bossHP.value?.minus(1)
        money.value = money.value?.plus(500)
    }

    fun startOnboarding() {
        currentState.value = FightState.ONBOARD_1
    }

    fun updateOnboarding() {
        currentState.value?.let {
            val nextStep = when (currentState.value) {
                FightState.ONBOARD_1 -> FightState.ONBOARD_2
                FightState.ONBOARD_2 -> FightState.ONBOARD_3
                FightState.ONBOARD_3 -> {
                    dataHolder.isFightOnboardingComplete = true
                    FightState.FIGHT
                }
                FightState.FIGHT -> FightState.FIGHT
                else -> FightState.FIGHT
            }
            currentState.value = nextStep
        }
    }

}

enum class FightState(val step: Int) {
    ONBOARD_1(1),
    ONBOARD_2(2),
    ONBOARD_3(3),
    FIGHT(4)
}