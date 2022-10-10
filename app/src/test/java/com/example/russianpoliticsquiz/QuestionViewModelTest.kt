package com.example.russianpoliticsquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.*
import org.junit.Test

class QuestionViewModelTest {
    @Test
    fun providesExpectedQuestionText() {
        val savedStateHandle = SavedStateHandle(mapOf("CURRENT_INDEX_KEY" to 5))
        val questionViewModel = QuestionViewModel(savedStateHandle)
        assertEquals(questionViewModel.currentQuestionText, R.string.question_1)
        questionViewModel.nextQuestion()
        assertEquals(questionViewModel.currentQuestionText, R.string.question_2)
    }
}