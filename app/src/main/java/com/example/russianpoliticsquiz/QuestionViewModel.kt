package com.example.russianpoliticsquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.math.abs

private const val TAG = "QuestionViewModel"
private const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
private const val USER_CHEATED_KEY = "USER_CHEATED_KEY"

class QuestionViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_1, true),
        Question(R.string.question_2, true),
        Question(R.string.question_3, false),
        Question(R.string.question_4, false),
        Question(R.string.question_5, true)
    )
    val isCurrentQuestionCheated: Boolean
    get() = questionBank[currentIndex].isCheated
    val currentQuestionAnswer: Boolean
    get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
    get() = questionBank[currentIndex].textResId
    val wasQuestionAnswered: Boolean
    get() = questionBank[currentIndex].isAnswered
    val questionsAmount: Int
    get() = questionBank.size
    private var currentIndex: Int
    get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
    set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)
    var rightAnswers = 0
    var yetAnswered = 0
    var cheatsLeft = 3
    var userCheated: Boolean
    get() = savedStateHandle[USER_CHEATED_KEY] ?: false
    set(value) = savedStateHandle.set(USER_CHEATED_KEY ,value)
    fun nextQuestion() {
        Log.d(TAG, "Updating question", Exception())
        currentIndex = ++currentIndex % questionsAmount
    }
    fun prevQuestion() {
        currentIndex = --currentIndex % questionsAmount
        if (currentIndex < 0) {
            currentIndex = questionsAmount - abs(currentIndex) % questionsAmount
        }
    }
    fun markAnswered() {
        questionBank[currentIndex].isAnswered = true
        yetAnswered++
    }
    fun markCheated() {
        questionBank[currentIndex].isCheated = true
        cheatsLeft--
    }
}