package com.mobile.hackatoners.maplevel.utils

enum class Region(val value: Int) {
    LEFT(3),
    RIGHT(2),
    MIDDLE(1),
    LOW(0);

    companion object {

        fun find(value: Int): Region {
            return checkNotNull(values().find { it.value == value })
        }
    }
}