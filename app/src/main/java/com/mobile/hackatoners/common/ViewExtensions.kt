package com.mobile.hackatoners.common

import android.view.View

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show(show: Boolean) {
    if (show) show() else hide()
}