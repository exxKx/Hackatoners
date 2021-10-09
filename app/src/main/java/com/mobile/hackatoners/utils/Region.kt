package com.mobile.hackatoners.utils

enum class Region(val value: Int) {
    FOREST(3),
    DESERT(2),
    WORLD(1),
    HILL(0);

    companion object {

        fun find(value: Int): Region {
            return checkNotNull(values().find { it.value == value })
        }
    }
}