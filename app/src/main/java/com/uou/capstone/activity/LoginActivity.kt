package com.uou.capstone.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.uou.capstone.api.auth.AuthService
import com.uou.capstone.databinding.ActivityLoginBinding
import com.uou.capstone.service.GoogleLoginManager
import com.uou.capstone.service.KakaoLoginManager


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
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
    }

}