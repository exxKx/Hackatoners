package com.mobile.hackatoners.maplevel.utils

enum class Region(val value: Int) {
    HIGH(2),
    MIDDLE(1),
    LOW(0);

    companion object {

        fun find(value: Int): Region {
            return checkNotNull(values().find { it.value == value })
        }
    }
}