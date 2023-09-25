package com.uou.capstone.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.uou.capstone.R
import com.uou.capstone.api.auth.AuthService
import com.uou.capstone.api.auth.email.request.PostCodeToEmailReq
import com.uou.capstone.databinding.ActivitySignUpBinding
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {
    companion object {
        const val LOG_TAG = "SignUpActivity"
    }

    private var authCode: String = ""
    private val authService = AuthService()
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.signUpEmailValidationBtn.setOnClickListener {
            if (binding.signUpHiddenEmailValidationCl.visibility != View.VISIBLE) {
                binding.signUpHiddenEmailValidationCl.visibility = View.VISIBLE
                sendCode()
            }
        }
        binding.signUpEmailValidationNumberBtn.setOnClickListener {
            handleValidationButtonClick()
        }
    }

    private fun handleValidationButtonClick() {
        when {
            binding.signUpEmailValidationNumberEt.text?.toString() == authCode -> {
                binding.signUpEmailValidationNumberBtn.apply {
                    isClickable = false
                    text = "완료"
                    setBackgroundResource(R.drawable.solid_button4)
                }
                Toast.makeText(this, "인증에 성공했습니다", Toast.LENGTH_SHORT).show()
            }
            else -> {
                authCode = ""
                binding.signUpEmailValidationNumberBtn.text = "재전송"
                Toast.makeText(this, "인증에 실패했습니다", Toast.LENGTH_SHORT).show()
                sendCode()
            }
        }
    }

    private fun sendCode() {
        val req = PostCodeToEmailReq(email = binding.signUpEmailEt.text.toString())
        // 코루틴 (비동기)
        lifecycleScope.launch {
            try {
                val response = authService.sendCodeToEmail(req)
                response.result?.authCode?.let {
                    authCode = it
                    Log.d(LOG_TAG, "AuthCode : $it")
                }
            } catch (e: Exception) {
                // Handle exception
                Log.e(LOG_TAG, "Error sending code", e)
            }
        }
    }
}