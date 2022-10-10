package com.example.russianpoliticsquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val USER_CHEATED_KEY = "USER_CHEATED_KEY"

class CheatViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {
    var userCheated: Boolean
    get() = savedStateHandle[USER_CHEATED_KEY] ?: false
    set(value) = savedStateHandle.set(USER_CHEATED_KEY, value)
}