package com.example.russianpoliticsquiz

import android.content.Context
import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.russianpoliticsquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val questionViewModel: QuestionViewModel by viewModels()
    private val cheatActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && !questionViewModel.isCurrentQuestionCheated) {
                val isCheated = result.data?.getBooleanExtra(
                    EXTRA_USER_CHEATED, false) ?: false
                if (isCheated) questionViewModel.markCheated()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) blurCheatButton()
        binding.tvApiLevel.text = "API level ${Build.VERSION.SDK_INT}"
        showQuestion()
        with(binding) {
            btnTrue.setOnClickListener { checkAnswer(userReply = true) }
            btnFalse.setOnClickListener { checkAnswer(userReply = false) }
            btnNext.setOnClickListener { nextOnClick() }
            btnPrev.setOnClickListener { prevOnClick() }
            btnCheat.setOnClickListener { startCheatActivity() }
        }
    }

    private fun checkAnswer(userReply: Boolean) {
        questionViewModel.markAnswered()
        checkAndBlockBtn()
        var toastText = R.string.incorrect_toast
        if (questionViewModel.isCurrentQuestionCheated) {
            toastText = R.string.judgment_toast
        } else if (userReply == questionViewModel.currentQuestionAnswer) {
            toastText = R.string.correct_toast
            questionViewModel.rightAnswers++
        }

        Snackbar.make(
            binding.root,
            toastText,
            Snackbar.LENGTH_SHORT)
            .show()

        if (questionViewModel.yetAnswered == questionViewModel.questionsAmount) {
            Snackbar.make(
                binding.root,
                "${(questionViewModel.rightAnswers.toFloat() /
                        questionViewModel.questionsAmount * 100).toInt()} % Correct",
                Snackbar.LENGTH_LONG)
                .show()
        }
    }
    private fun showQuestion() {
        binding.tvQuestion.setText(questionViewModel.currentQuestionText)
        checkAndBlockBtn()
    }
    private fun nextOnClick() {
        questionViewModel.nextQuestion()
        showQuestion()
    }
    private fun prevOnClick() {
        questionViewModel.prevQuestion()
        showQuestion()
    }

    private fun checkAndBlockBtn() {
        if (questionViewModel.wasQuestionAnswered) {
            binding.btnTrue.isEnabled = false
            binding.btnFalse.isEnabled = false
        } else {
            binding.btnTrue.isEnabled = true
            binding.btnFalse.isEnabled = true
        }
        if (questionViewModel.cheatsLeft <= 0)
            binding.btnCheat.isEnabled = false
    }

    private fun startCheatActivity() {
        val answerIsTrue = questionViewModel.currentQuestionAnswer
        val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
//        startActivity(intent)
        cheatActivityLauncher.launch(intent)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun blurCheatButton() {
        val effect = RenderEffect.createBlurEffect(
            10.0f,
            10.0f,
            Shader.TileMode.CLAMP
        )
        binding.btnCheat.setRenderEffect(effect)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}