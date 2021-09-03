package com.example.waleedn_oblig1

object Constants {

    fun getQuestions(): MutableList<Question>
    {
        var questionList: MutableList<Question> = mutableListOf()
        val q1 = Question(1,"UK stands for United Kingdom ",  true,  false,  true)
        val q2 = Question(2,"The capital of Norway is Oslo", true,  false,  true)
        val q3 = Question(3,"This Application is written in Java",  true,  false,  false)
        val q4 = Question(4,"End", optionOne = true, optionSecend = false, correctAnswer = true)
        questionList.add(q1)
        questionList.add(q2)
        questionList.add(q3)
        questionList.add(q4)

        return questionList
    }
}
