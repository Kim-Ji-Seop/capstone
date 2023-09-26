package com.uou.capstone.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.MotionEffect
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
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private val authService = AuthService()
    companion object {
        const val LOG_TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginCreateBtn.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        // Kakao 로그인
        binding.loginKakaoBtn.setOnClickListener{
            loginKakao()
        }
        // --------------------
        // Google 로그인
        // --------------------
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    // Kakao 로그인
    private fun loginKakao() {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                // 토큰 정보 보기
                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                    if (error != null) {
                        Log.e(TAG, "토큰 정보 보기 실패", error)
                    }
                    else if (tokenInfo != null) {
                        Log.i(TAG, "토큰 정보 보기 성공" +
                                "\n회원번호: ${tokenInfo.id}" +
                                "\n만료시간: ${tokenInfo.expiresIn} 초")
                    }
                }
                // 사용자 정보 요청 (기본)
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error)
                    }
                    else if (user != null) {
                        Log.i(TAG, "사용자 정보 요청 성공" +
                                "\n회원번호: ${user.id}" +
                                "\n이메일: ${user.kakaoAccount?.email}" +
                                "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                        val req = PostKakaoSdkLoginReq(
                            profileImg = user.kakaoAccount?.profile?.thumbnailImageUrl.toString(),
                            email = user.kakaoAccount?.email.toString(),
                            nickname = user.kakaoAccount?.profile?.nickname.toString(),
                            provider = "KAKAO")
                        // 코루틴 (비동기)
                        lifecycleScope.launch {
                            try {
                                val response = authService.loginWithKakaoSdk(req)
                                when (response.code) {
                                    200 -> {
                                        response.result?.let { result ->
                                            Log.d(LOG_TAG, "Result : $result")
                                        }
                                    }
                                }
                            } catch (e: HttpException) {

                            }

                        }
                    }
                }

            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }
    // --------------------

    // Google 로그인
    // --------------------
}