package com.example.waleedn_oblig1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() , View.OnClickListener{

    private var mCurrentPosition :Int = 1
    private var mQuestionList:MutableList<Question>? = null
    private var rightAnswer:Boolean = false
    private var p :Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        mQuestionList = Constants.getQuestions()

        setQuestion()
        btnTrue.setOnClickListener (this)
        btnFalse.setOnClickListener (this)
        btnRestart.setOnClickListener(this)

    }
    private fun  setQuestion(valid:Boolean)
    {

        val question = mQuestionList!![mCurrentPosition-1]

        tvQuestion.text = question!!.question
        btnTrue.text = question.optionOne.toString()
        btnFalse.text = question.optionSecend.toString()
        rightAnswer = question.correctAnswer.toString().toBoolean()
        if (question.id == 4)
        {
            btnFalse.isEnabled = false
            btnTrue.isEnabled = false
            tvPoints.text = "You Got: $p/3 points!"
        }

        if (valid==rightAnswer)
        {
            ++p
            tvPoints.text = "Points: $p/3 "

        }
        else
        {
            tvPoints.text = "Points: $p/3 "
        }


    }
    private fun  setQuestion()
    {

        btnFalse.isEnabled = true
        btnTrue.isEnabled = true
        mCurrentPosition = 1
        val question = mQuestionList!![mCurrentPosition-1]

        tvQuestion.text = question!!.question
        btnTrue.text = question.optionOne.toString()
        btnFalse.text = question.optionSecend.toString()

        p = 0
        tvPoints.text = "$p/3 Points"

    }

    override fun onClick(v: View?) {
        when(v?.id)
        {

            R.id.btnTrue ->{
                when{
                    mCurrentPosition<=mQuestionList!!.size ->
                    {

                        setQuestion(true)
                        nextQ()
                    }
                }
            }
            R.id.btnFalse ->
            {

                when{
                    mCurrentPosition<=mQuestionList!!.size ->
                    {

                        setQuestion(false)
                        nextQ()
                    }
                }



            }
            R.id.btnRestart ->
            {
                setQuestion()
            }
        }
    }
    private fun nextQ()
    {
        mCurrentPosition++
        val question = mQuestionList!![mCurrentPosition-1]

        tvQuestion.text = question!!.question
        btnTrue.text = question.optionOne.toString()
        btnFalse.text = question.optionSecend.toString()
        rightAnswer = question.correctAnswer.toString().toBoolean()
        if (question.id == 4)
        {
            btnFalse.isEnabled = false
            btnTrue.isEnabled = false
            tvPoints.text = "You Got: $p/3 points!"
        }

    }

}