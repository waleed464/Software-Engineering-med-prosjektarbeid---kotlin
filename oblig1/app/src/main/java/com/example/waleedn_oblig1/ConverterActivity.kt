package com.example.waleedn_oblig1

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_converter.*
import java.math.BigDecimal
import java.math.RoundingMode

class ConverterActivity : AppCompatActivity() {
    private val fluid =  0.02957
    private val  cup = 0.23659
    private val  gallon = 3.78541
    private val  hogshead = 238.481
    var valu = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                valu = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        btnConvert.setOnClickListener {
            if (etLiter.text.toString().isNotEmpty())
            {
                when (valu) {
                    0 -> {
                        val num = etLiter.text.toString().toDouble()
                        findValue(num,fluid)
                    }
                    1 -> {
                        val num = etLiter.text.toString().toDouble()
                        findValue(num,cup)
                    }
                    2 -> {
                        val num = etLiter.text.toString().toDouble()
                        findValue(num,gallon)
                    }
                    3 -> {
                        val num = etLiter.text.toString().toDouble()
                        findValue(num,hogshead)
                    }
                }
            }
            else
            {
                textV.visibility = View.GONE
                Toast.makeText(applicationContext,"Please Enter Value First..", Toast.LENGTH_SHORT).show()

            }
            hideKeyboard()

        }
        btnThirdActivity.setOnClickListener {
            val intent = Intent(this,QuizActivity::class.java)
            startActivity(intent)
        }
    }
    private fun findValue(number:Double,unit:Double)
    {
        val total = BigDecimal(number*unit).setScale(2, RoundingMode.HALF_EVEN)
        textV.visibility = View.VISIBLE
        textV.text = "$total liters"

    }
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}