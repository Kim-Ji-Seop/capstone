package com.uou.capstone.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.uou.capstone.R
import com.uou.capstone.api.auth.AuthService
import com.uou.capstone.api.auth.email.request.PostCodeToEmailReq
import com.uou.capstone.api.auth.email.request.PostSignUpWithEmailReq
import com.uou.capstone.databinding.ActivitySignUpBinding
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

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
        binding.signUpBtn.setOnClickListener {
            signUp()
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
                showToast("인증에 성공했습니다")
            }
            else -> {
                authCode = ""
                binding.signUpEmailValidationNumberBtn.text = "재전송"
                showToast("인증에 실패했습니다")
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

    private fun signUp() {
        when {
            !binding.signUpPasswordEt.text?.toString().equals(binding.signUpPasswordValidationEt.text.toString()) -> {
                showToast("비밀번호가 다릅니다")
                return
            }
        }
        val req = PostSignUpWithEmailReq(
            email = binding.signUpEmailEt.text.toString(),
            password = binding.signUpPasswordEt.text.toString(),
            name = binding.signUpNameEt.text.toString(),
            nickname = binding.signUpNicknameEt.text.toString()
        )
        // 코루틴 (비동기)
        lifecycleScope.launch {
            try {
                val response = authService.signUpWithEmail(req)
                when (response.code) {
                    200 -> {
                        response.result?.getUserIdx()?.let { userIdx ->
                            Log.d(LOG_TAG, "UserIdx : $userIdx")
                        }
                        // API 호출에 성공했으므로 로그인 액티비티로 이동
                        val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else -> {
                        showToast("알 수 없는 오류가 발생했습니다.")
                    }
                }
            } catch (e: HttpException) {
                Log.e(LOG_TAG, "Error sending code", e)

                val errorJsonString = e.response()?.errorBody()?.string()

                val jsonObject = JSONObject(errorJsonString ?: "")
                val userDefinedCode = jsonObject.optInt("code", -1)
                val userMessage = jsonObject.optString("message", "알 수 없는 오류가 발생했습니다.")

                when (e.code()) {
                    400 -> {
                        when (userDefinedCode) {
                            4000 -> showToast("이메일 형식이 올바르지 않습니다.")
                            4001 -> showToast("비밀번호 형식이 올바르지 않습니다.")
                            else -> showToast(userMessage)
                        }
                    }
                    409 -> {
                        when (userDefinedCode) {
                            4002 -> showToast("이미 존재하는 이메일입니다.")
                            else -> showToast(userMessage)
                        }
                    }
                    500 -> {
                        when (userDefinedCode) {
                            5000 -> showToast("비밀번호 암호화에 실패하였습니다.")
                            5100 -> showToast("데이터베이스 오류가 발생했습니다.")
                            else -> showToast(userMessage)
                        }
                    }
                    else -> showToast("네트워크 오류가 발생했습니다.")
                }
            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}