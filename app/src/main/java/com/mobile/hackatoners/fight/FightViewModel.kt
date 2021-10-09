package com.mobile.hackatoners.fight

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FightViewModel : ViewModel() {

    val playerHP :MutableLiveData<Int> = MutableLiveData(4)
    val bossHP :MutableLiveData<Int> = MutableLiveData(5)
    val money :MutableLiveData<Int> = MutableLiveData(1000)


    fun bossAttack(){
        playerHP.value = playerHP.value?.minus(1)
    }

    fun playerAttack(){
        bossHP.value = bossHP.value?.minus(1)
    }

}