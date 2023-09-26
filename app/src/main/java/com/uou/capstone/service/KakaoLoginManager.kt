package com.uou.capstone.service

import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.Constants
import com.kakao.sdk.user.UserApiClient
import com.uou.capstone.activity.LoginActivity
import com.uou.capstone.api.auth.AuthService
import com.uou.capstone.api.auth.kakao.request.PostKakaoSdkLoginReq
import kotlinx.coroutines.launch
import retrofit2.HttpException

class KakaoLoginManager(private val activity: LoginActivity, private val authService: AuthService, private val lifecycleScope: LifecycleCoroutineScope) {

    fun login() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(LoginActivity.LOG_TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                getKakaoTokenInfo()
                getKakaoUserInfo()
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(activity)) {
            UserApiClient.instance.loginWithKakaoTalk(activity) { token, error ->
                if (error != null) {
                    Log.e(Constants.TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(activity, callback = callback)
                } else if (token != null) {
                    Log.i(Constants.TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(activity, callback = callback)
        }
    }

    private fun getKakaoUserInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(Constants.TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i(
                    Constants.TAG, "사용자 정보 요청 성공" +
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
                                    Log.d(LoginActivity.LOG_TAG, "Result : $result")
                                }
                            }
                        }
                    } catch (e: HttpException) {

                    }

                }
            }
        }
    }

    private fun getKakaoTokenInfo() {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.e(Constants.TAG, "토큰 정보 보기 실패", error)
            }
            else if (tokenInfo != null) {
                Log.i(
                    Constants.TAG, "토큰 정보 보기 성공" +
                        "\n회원번호: ${tokenInfo.id}" +
                        "\n만료시간: ${tokenInfo.expiresIn} 초")
            }
        }
    }
}
