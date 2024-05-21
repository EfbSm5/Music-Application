package com.example.mymusicapplication



class Questions(private val question: String, private val answer: List<String>) {
    fun getQuestion(): String {
        return question
    }
    fun getAnswer(): List<String> {
        return answer
    }
}