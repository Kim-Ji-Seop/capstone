package com.uou.capstone.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.uou.capstone.api.auth.AuthService
import com.uou.capstone.api.auth.email.request.PostEmailLoginReq
import com.uou.capstone.api.auth.google.request.PostGoogleSdkLoginReq
import com.uou.capstone.common.SharedPreferenceManger
import com.uou.capstone.databinding.ActivityLoginBinding
import com.uou.capstone.service.GoogleLoginManager
import com.uou.capstone.service.KakaoLoginManager
import kotlinx.coroutines.launch
import retrofit2.HttpException


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private val authService = AuthService()

    private val googleLoginManager = GoogleLoginManager(this, authService, lifecycleScope)
    private val kakaoLoginManager = KakaoLoginManager(this, authService, lifecycleScope)
    companion object {
        const val LOG_TAG = "LoginActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        googleLoginManager.initializeGoogleSignInClient()

        binding.loginCreateBtn.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.loginKakaoBtn.setOnClickListener {
            kakaoLoginManager.login()
        }
        binding.loginGoogleBtn.setOnClickListener {
            googleLoginManager.signIn()
        }
        binding.loginBtn.setOnClickListener {
            loginWithEmail()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun loginWithEmail(){
        val req = PostEmailLoginReq(
            email = binding.loginIdEtv.text.toString(),
            password = binding.loginPwEtv.text.toString(),
            provider = "EMAIL")
        lifecycleScope.launch {
            try {
                val response = authService.loginWithEmail(req)
                when (response.code) {
                    200 -> {
                        response.result?.let { result ->
                            SharedPreferenceManger.setLoginInfo(
                                this@LoginActivity,
                                result.getTokenDto().getAccessToken(),
                                result.getTokenDto().getRefreshToken(),
                                result.getUserIdx(),
                                result.getNickname(),
                                "EMAIL"
                            )

                            // 액티비티 이동
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            } catch (e: HttpException) {
                Log.e("Email Login Error :",e.message())
            }
        }
    }

}