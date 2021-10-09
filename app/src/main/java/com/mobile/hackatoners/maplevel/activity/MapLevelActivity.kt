package com.mobile.hackatoners.maplevel.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mobile.hackatoners.R
import com.mobile.hackatoners.maplevel.fragment.ChooseRegionFragment
import com.mobile.hackatoners.maplevel.utils.ScreenChanger

class MapLevelActivity : AppCompatActivity(), ScreenChanger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maplevel)

        replaceWith(ChooseRegionFragment(), false)
    }

    override fun replaceWith(fragment: Fragment, addToBackStack: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            if (addToBackStack) {
                addToBackStack(fragment.javaClass.simpleName)
            }
            commit()
        }
    }
}