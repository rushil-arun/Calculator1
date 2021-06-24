package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException


class MainActivity : AppCompatActivity() {

    var lastNumeric = false
    var lastDot = false

    private val textInput: TextView = findViewById<TextView>(R.id.calcInput)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onDigit(view: View){
        textInput.append((view as Button).text)
        lastNumeric = true
        Toast.makeText(this, "Button Works", Toast.LENGTH_SHORT).show()
    }

    fun clear(view: View){
        textInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimal(view: View){
        if (lastNumeric && !lastDot){
            textInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        if (lastNumeric && !(isOperatorAdded(textInput.text.toString()))){ // we make sure there is something after decimal, and there aren't already operators
            textInput.append((view as Button).text)
            lastDot = false
            lastNumeric = false
        }
    }

    fun onEqual(view: View){
        if (lastNumeric){
            var textValue = textInput.text.toString()
            var startsWithMinus = false
            try{
                if (textValue.startsWith("-")){
                    startsWithMinus = true
                    textValue = textValue.substring(1)
                }
                if(textValue.contains("-")){
                    var split = textValue.split("-")

                    if (startsWithMinus){
                        textInput.text = removeZeroes((-split[0].toDouble() - split[1].toDouble()).toString())
                    } else {
                        textInput.text = removeZeroes((split[0].toDouble() - split[1].toDouble()).toString())
                    }
                } else if(textValue.contains("+")){
                    var split = textValue.split("+")

                    if (startsWithMinus){
                        textInput.text = removeZeroes((-split[0].toDouble() + split[1].toDouble()).toString())
                    } else {
                        textInput.text = removeZeroes((split[0].toDouble() + split[1].toDouble()).toString())
                    }
                } else if(textValue.contains("*")){
                    var split = textValue.split("*")

                    if (startsWithMinus){
                        textInput.text = removeZeroes((-split[0].toDouble() * split[1].toDouble()).toString())
                    } else {
                        textInput.text = removeZeroes((split[0].toDouble() * split[1].toDouble()).toString())
                    }
                } else if(textValue.contains("/")){
                    var split = textValue.split("/")

                    if (startsWithMinus){
                        textInput.text = removeZeroes((-split[0].toDouble() / split[1].toDouble()).toString())
                    } else {
                        textInput.text = removeZeroes((split[0].toDouble() / split[1].toDouble()).toString())
                    }
                }



            } catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroes(result: String) : String{
        var value = result
        if (value.contains(".0")){
            value = result.substring(0, result.length - 2) // geting rid of the decimal point and the extra 0
        }
        return value
    }
    private fun isOperatorAdded(value: String) : Boolean { // we made this private because we only use it in this file
        return if (value.startsWith("-")){
            false
        } else {
            value.contains("/") || value.contains("+") || value.contains("-") || value.contains("*")
        }
    }


}