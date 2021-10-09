package com.mobile.hackatoners.utils

import androidx.annotation.StringRes
import com.mobile.hackatoners.R

enum class Questions(
    val id: Int,
    @StringRes val question: Int,
    val isThreeOptions: Boolean,
    val answers: List<Int>,
    val rightAnswers: List<Int>
) {

    QUESTION_1(
        1,
        R.string.question_1_title,
        false,
        listOf(R.string.question_1_answer_1, R.string.question_1_answer_2),
        listOf(1)
    ),
    QUESTION_2(
        2,
        R.string.question_2_title,
        false,
        listOf(R.string.question_2_answer_1, R.string.question_2_answer_2),
        listOf(1)
    ),
    QUESTION_3(
    3,
    R.string.question_3_title,
    false,
    listOf(R.string.question_3_answer_1, R.string.question_3_answer_2),
        listOf(1)
    ),
    QUESTION_4(
        4,
        R.string.question_4_title,
        true,
        listOf(R.string.question_4_answer_1, R.string.question_4_answer_2,R.string.question_4_answer_3),
        listOf(3)
    ),
    QUESTION_5(
        5,
        R.string.question_5_title,
        true,
        listOf(R.string.question_5_answer_1, R.string.question_5_answer_2,R.string.question_5_answer_3),
        listOf(3)
    ),
    QUESTION_6(
        6,
        R.string.question_6_title,
        true,
        listOf(R.string.question_6_answer_1, R.string.question_6_answer_2,R.string.question_6_answer_3),
        listOf(1)
    ),
    QUESTION_7(
        7,
        R.string.question_7_title,
        false,
        listOf(R.string.question_7_answer_1, R.string.question_7_answer_2),
        listOf(2)
    ),
    QUESTION_8(
        8,
        R.string.question_8_title,
        true,
        listOf(R.string.question_8_answer_1, R.string.question_8_answer_2,R.string.question_8_answer_3),
        listOf(2)
    ),
    QUESTION_9(
        9,
        R.string.question_9_title,
        true,
        listOf(R.string.question_9_answer_1, R.string.question_9_answer_2,R.string.question_9_answer_3),
        listOf(1,3)
    ),
    QUESTION_10(
        10,
        R.string.question_10_title,
        true,
        listOf(R.string.question_10_answer_1, R.string.question_10_answer_2,R.string.question_10_answer_3),
        listOf(2)
    )

}