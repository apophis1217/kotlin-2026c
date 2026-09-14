package com.kotlinbascis

import android.icu.text.UnicodeSetSpanner
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kotlinbascis.ui.theme.KotlinBascisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinBascisTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        week03Variables()
        week03Functions()
    }
}

private fun week03Variables() {
    println("============Week 03: Variables==========")

    val courseName = "Mobile Programing" // Java final keyword = val
    // courseName = "DataStructure" // error

    var week = 2
    week = 3
    println("Course : $courseName")
    println("Week : $week")


}
//fun week03Variables() {
//    println("Nickname: $nickname ${nickname?.length}")
//}

private fun week03Functions(){
//    println("Week 03: Functions")
//
//    fun greet(name: String) = "Hello, $name!"
//
//    println(greet("Android developer"))

    println("========== Kotlin Functions ==========")

    fun greet(name: String): String { // string -> 리턴 타입(소괄호 닫고 콜론 다음 온다)
        return "Hello, $name!"
    }

    fun add(a: Int, b: Int) = a + b // '=' : return 역할

    fun introduce(name: String, age: Int = 19){ // -> return이 없으니까 그냥 호출만함.
        println("My name is $name and I'm $age years old")
    }

    println(greet("Kotlin"))
    println("Sum: ${add(5, -71)}")
    introduce("Kim", 7)
    introduce("Park") // 호출만 하고, age 값을 따로 지정 안했기 때문에 기본 지정 값 19 출력.
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinBascisTheme {
        Greeting("Android")
    }
}