package com.mobile.hackatoners.fight

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.hackatoners.utils.DataHolder
import com.mobile.hackatoners.utils.Questions
import com.mobile.hackatoners.utils.SingleLiveEvent
import kotlinx.coroutines.*

class FightViewModel(application: Application) : AndroidViewModel(application) {

    private val dataHolder by lazy { DataHolder.getInstance(application.applicationContext) }

    val playerHP: MutableLiveData<Int> = MutableLiveData(4)
    val bossHP: MutableLiveData<Int> = MutableLiveData(5)
    val money: MutableLiveData<Int> = MutableLiveData(20000)
    var answerCount = 0
    var rightAnswerCount = 0
    val currentState: MutableLiveData<FightState> = MutableLiveData(FightState.FIGHT)
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    var timeOnBoss = 0
    val inflationEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    var inflationSpent = 0
    val allQuestions = Questions.values().toMutableList()
    val currentQuestion: MutableLiveData<Questions> = MutableLiveData()

    init {
        if (!dataHolder.isFightOnboardingComplete) {
            startOnboarding()
        } else {
            startTimer()
        }
        getRandomQuestion()
    }


    fun bossAttack() {
        answerCount++
        playerHP.value = playerHP.value?.minus(1)
    }

    fun getRandomQuestion() {
        val quest = allQuestions.random()
        allQuestions.remove(quest)
        currentQuestion.value = quest
    }

    fun playerAttack() {
        answerCount++
        rightAnswerCount++
        bossHP.value = bossHP.value?.minus(1)
        money.value = money.value?.plus(500)

    }

    fun startOnboarding() {
        currentState.value = FightState.ONBOARD_1
    }

    fun startTimer() {
        scope.launch {
            while (isActive) {
                delay(1000)
                timeOnBoss++
                if (timeOnBoss % 5 == 0)
                    inflation()
                Log.w("spendingTime", "time $timeOnBoss")
            }
        }
    }

    fun inflation() {
        money.postValue(money.value?.minus(200))
        inflationEvent.postValue(null)
        inflationSpent += 200
    }

    fun updateOnboarding() {
        currentState.value?.let {
            val nextStep = when (currentState.value) {
                FightState.ONBOARD_1 -> FightState.ONBOARD_2
                FightState.ONBOARD_2 -> FightState.ONBOARD_3
                FightState.ONBOARD_3 -> {
                    dataHolder.isFightOnboardingComplete = true
                    startTimer()
                    FightState.FIGHT
                }
                FightState.FIGHT -> FightState.FIGHT
                else -> FightState.FIGHT
            }
            currentState.value = nextStep
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

}

enum class FightState(val step: Int) {
    ONBOARD_1(1),
    ONBOARD_2(2),
    ONBOARD_3(3),
    FIGHT(4)
}