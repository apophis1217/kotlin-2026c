package com.kotlinbascis

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
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
        //week03Variables()
        //week03Functions()
        week04Classes()
        //week04Collections()
    }
}

private fun week04Collections(){
    println("============ Kotlin Collections ============")

    val fruits = listOf("apple", "banana", "orange") // 불변
    val mutableFruits = mutableListOf("kiwi", "watermelon") // 가변

    //fruits.add("kiwi") // immutable
    println("Fruits: $fruits")
    println("Mutable Fruits : $mutableFruits")

    val scores = mapOf("Kim" to 100, "Park" to 96, "Lee" to 97)
    println("Scores : $scores") // Scores : {Kim=100, Park=96, Lee=97}

    for(fruit in mutableFruits){
        println("I like $fruit")
    }
    scores.forEach {(name, score) -> println("$name scored $score")}
    fruits.forEach { fruit -> println("$fruit") } //매개변수가 하나라서 () 생략
}



private fun week04Classes(){
    Log.d("KotlinWeek04", "== Kotlin Classes ==")

    class Person(val name: String, var age: Int){
        fun introduce(){
            Log.d("KotlinWeek04", "안녕하세요, $name ($age 세)입니다.")
        }
        fun birthday(){
            age++
            Log.d("KotlinWeek04", "$name 의 생일! 이제 $age 세...")
        }
    }
    val person1 = Person("홍길동", 27)
    person1.introduce()
    person1.birthday()

    class Animal(var species: String){
        var weight: Double = 0.0
        constructor(species: String, weight: Double) : this(species){
            this.weight = weight
            Log.d("KotlinWeek04", "$species 의 무게 : $weight kg")
        }
        fun makeSound(){
            Log.d("KotlinWeek04", "$species 가 소리를 냅니다.")
        }
    }
    val puppy = Animal("웰시코기", 10.5)
    puppy.makeSound()
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

    //println(greet("Kotlin"))
    //println("Sum: ${add(5, -71)}")
    //introduce("Kim", 7)
    //introduce("Park") // 호출만 하고, age 값을 따로 지정 안했기 때문에 기본 지정 값 19 출력.

    fun printAll(vip: Boolean, name: String){
        println("$vip, $name")
    }
    fun printMany(vararg msg: String){
        for(m in msg) println(m)
    }

    //printAll(true, "dy") // 지정으로 출력하는 경우 위치는 상광없음.
    printAll(name="mirae", vip=true) // named argument

    printMany("A", "B", "C", "D")
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