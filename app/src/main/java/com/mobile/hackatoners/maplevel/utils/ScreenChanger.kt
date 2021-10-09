package com.mobile.hackatoners.maplevel.utils

import androidx.fragment.app.Fragment

interface ScreenChanger {
    fun replaceWith(fragment: Fragment, addToBackStack: Boolean)
}