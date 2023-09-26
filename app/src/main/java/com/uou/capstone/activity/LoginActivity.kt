package com.uou.capstone.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.Constants.TAG
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.Account
import com.uou.capstone.api.auth.AuthService
import com.uou.capstone.api.auth.email.request.PostCodeToEmailReq
import com.uou.capstone.api.auth.kakao.request.PostKakaoSdkLoginReq
import com.uou.capstone.databinding.ActivityLoginBinding
import com.uou.capstone.service.GoogleLoginManager
import com.uou.capstone.service.KakaoLoginManager
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private val authService = AuthService()

    private val googleLoginManager = GoogleLoginManager(this, authService)
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
        googleLoginManager.checkExistingLogin()
    }

}