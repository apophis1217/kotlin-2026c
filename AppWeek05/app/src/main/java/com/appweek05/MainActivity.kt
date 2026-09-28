package com.appweek05

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textViewResult = findViewById<TextView>(R.id.tvResult)
        val buttonCalculate = findViewById<Button>(R.id.btnCalculate)
        val editText = findViewById<EditText>(R.id.etDan)


        buttonCalculate.setOnClickListener {
            val inputText = editText.text.toString()


            if (inputText.isEmpty()) {
                Toast.makeText(this, "숫자를 입력하시오", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            val dan = inputText.toInt()
            val result = StringBuilder()
            result.append("==== $dan 단 ====\n\n")

            for (i in 1..9) {
                result.append("$dan x $i = ${dan * i}\n")
            }


            textViewResult.text = result.toString()
        }
    }
}