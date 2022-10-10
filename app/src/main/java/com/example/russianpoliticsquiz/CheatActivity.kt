package com.example.russianpoliticsquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.russianpoliticsquiz.databinding.ActivityCheatBinding

private const val EXTRA_ANSWER_IS_TRUE = "com.example.russianpoliticsquiz.answer_is_true"
const val EXTRA_USER_CHEATED = "com.example.russianpoliticsquiz.user_cheated"


class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding
    private val cheatViewModel: CheatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        binding.btnShowAnswer.setOnClickListener { btnShowAnswerOnClick(answerIsTrue) }
        updateInterface(answerIsTrue)
        setAnswerShownResult(cheatViewModel.userCheated)
    }

    private fun btnShowAnswerOnClick(answer: Boolean) {
        if (!cheatViewModel.userCheated) {
            binding.tvAnswer.text = answer.toString()
            setAnswerShownResult(wasAnswerShown = true)
            binding.btnShowAnswer.isEnabled = false
            cheatViewModel.userCheated = true
        }
    }

    private fun setAnswerShownResult(wasAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_USER_CHEATED, wasAnswerShown)
        }
        setResult(RESULT_OK, data)
    }

    private fun updateInterface(answer: Boolean) {
        if (cheatViewModel.userCheated) {
            binding.tvAnswer.text = answer.toString()
            binding.btnShowAnswer.isEnabled = false
        }
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity().javaClass).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}