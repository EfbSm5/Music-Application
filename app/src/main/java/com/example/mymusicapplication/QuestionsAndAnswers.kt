package com.example.mymusicapplication



class QuestionsAndAnswers(private val question: String, private val potentialAnswers: List<String>) {
    fun getQuestion(): String {
        return question
    }
    fun getPotentialAnswer(): List<String> {
        return potentialAnswers
    }
}