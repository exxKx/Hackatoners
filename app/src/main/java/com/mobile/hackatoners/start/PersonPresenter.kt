package com.mobile.hackatoners.start

import com.mobile.hackatoners.utils.DataHolder

class PersonPresenter(val view: PersonFragment, val dataHolder: DataHolder) {

    private var currentQuestionIndex = 0
    private var hp = 1
    private var coins = 0
    private var girl : Boolean = false

    fun chooseClicked(choose: Int) {
        when (data[currentQuestionIndex].id) {
            1 -> {
                girl = choose == 2
                if (girl) {
                    view.showGirl()
                } else {
                    view.showMan()
                }
                dataHolder.girl = girl
            }
            2 -> {
                hp++
                view.showHp(hp)
            }
            3 -> {
                    coins = 3000
                    view.showCoins(coins)
            }
            4 -> {
                hp--
                view.showHp(hp)
            }
            7, 8, 9 -> {
                hp++
                view.showHp(hp)
            }

        }

        currentQuestionIndex++

        if (data.size - 1 < currentQuestionIndex) {
            dataHolder.coins = coins
            dataHolder.hp = hp
            view.showResult(girl)
        } else {

            view.showQuestion(data[currentQuestionIndex])
        }

    }

    fun init() {
        view.showQuestion(data[currentQuestionIndex])
    }


    // data
    private val question1 = Question(
        id = 1,
        text = "Привет! Я раньше тебя не видел в этих землях... В тени не видно, кто ты, подойди ближе!\n\nКто ты?",
        answers = listOf("Мужчина", "Женщина")
    )

    private val question2 = Question(
        id = 2,
        text = "Укажите ваш возраст",
        answers = listOf("от 18 до 24", "от 25 до 36", "от 37 до 60", "старше 61")
    )

    private val question3 = Question(
        id = 3,
        text = "Чем занимаетесь?",
        answers = listOf("Предприниматель", "Специалист", "ИТ сфера", "Менеджмент")
    )

    private val question4 = Question(
        id = 4,
        text = "Отлично, давайте настроем уровень сложности под вас! Расскажите немного о себе, вы склонны к импульсивным решениям?",
        answers = listOf("Да", "Нет")
    )

    private val question5 = Question(
        id = 5,
        text = "Ваш знакомый звонит вам и предлагает вложить 2000 монет в новую классную компанию под 300% годовых по его реферальной ссылке. Что вы ответите ему?",
        answers = listOf(
            "Звучит как отличная идея!",
            "Ничего не дам",
            "Слишком сложно, давайте дальше"
        )
    )

    private val question6 = Question(
        id = 6,
        text = "Брали ли кредит на покупку новых вещей?",
        answers = listOf("Да", "Нет")
    )

    private val question7 = Question(
        id = 7,
        text = "Ваши знакомые часто отмечали вашу хорошую интуицию и рациональный подход?",
        answers = listOf("Да", "Нет")
    )

    private val question8 = Question(
        id = 8,
        text = "Что вы любите читать?",
        answers = listOf(
            "Люблю читать художественную литературу",
            "Читаю новости, бизнес литературу",
            "Мемы"
        )
    )

    private val question9 = Question(
        id = 9,
        text = "Интересуетесь математикой?",
        answers = listOf("Да", "Нет")
    )

    private val question10 = Question(
        id = 10,
        text = "Почти закончили! Слова «волатильность», «Дивиденды», «оборот компании» вам знакомы?",
        answers = listOf("Да", "Нет")
    )

    private val question11 = Question(
        id = 11,
        text = "Отлично, а слова «Ebitda, «ROI», «ROA» вам знакомы?",
        answers = listOf("Мне они понятны", "Звучит как какие то ругательства")
    )

    private val question12 = Question(
        id = 12,
        text = "И последний вопрос — стакан на половину пуст или наполовину полон?",
        answers = listOf(
            "Наполовину пуст",
            "Наполовину полон",
            "Мой стакан формируется в зависимости от позиции"
        )
    )

    data class Question(val id: Int, val text: String, val answers: List<String>)

    val data: List<Question> = listOf(
        question1,
        question2,
        question3,
        question4,
        question5,
        question6,
        question7,
        question8,
        question9,
        question10,
        question11,
        question12
    )
    //end region
}