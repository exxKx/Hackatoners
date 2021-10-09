package com.mobile.hackatoners.start

class PersonPresenter(val view: PersonFragment) {

    // data
    private val question1 = Question(
        id = 1,
        text = "Привет! Я раньше тебя не видел в этих землях... В тени не видно, кто ты, подойди ближе!\n\nКто ты?",
        answers = listOf<String>("Мужчина", "Женщина")
    )

    private val question2 = Question(
        id = 2,
        text = "Расскажи, кто ты по жизни? Чем занимаешься?",
        answers = listOf<String>("Работаю на заводе", "У меня свой бизнес", "Выживаю от зарплаты до зарплаты", "Гребу деньги лопатой")
    )

    data class Question(val id: Int, val text: String, val answers: List<String>)
    //end region


    val data : List<Question> = listOf(
        question1, question2
    )

    private var currentQuestionIndex = 0

    fun chooseClicked(choose: Int) {
        currentQuestionIndex++

        if (data.size - 1 < currentQuestionIndex) {
            view.showResult()
        } else {
            view.showQuestion(data[currentQuestionIndex])
        }

    }

    fun init() {
        view.showQuestion(data[currentQuestionIndex])
    }
}