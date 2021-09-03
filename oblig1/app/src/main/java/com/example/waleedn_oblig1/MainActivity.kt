package com.example.waleedn_oblig1

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    var string = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCheck.setOnClickListener {
            string = etName.text.toString()
            if (string.trim().length>0)
            {
                if (isPalindromeName(string))
                {
                    tvName.visibility = View.VISIBLE
                    tvName.text = "Hi $string, your name is palindrome"
                }
                else
                {
                    tvName.visibility = View.VISIBLE
                    tvName.text = "Hi $string, your name is not palindrome"
                }


            }
            else{
                tvName.visibility = View.VISIBLE
                tvName.text = "please enter your name first..."
            }
            etName.text.clear()
            hideKeyboard()
        }

        btnNext.setOnClickListener {
            val intent = Intent(this,ConverterActivity::class.java)
            startActivity(intent)
        }

    }
    private fun isPalindromeName(isPalindrom:String): Boolean
    {
        val sb = StringBuilder(isPalindrom)

        val reverseName = sb.reverse().toString()

        return isPalindrom.equals(reverseName,ignoreCase = true)
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