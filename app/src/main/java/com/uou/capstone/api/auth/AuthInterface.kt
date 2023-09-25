package com.uou.capstone.api.auth

import com.uou.capstone.api.auth.email.request.PostCodeToEmailReq
import com.uou.capstone.api.auth.email.request.PostSignUpWithEmailReq
import com.uou.capstone.api.auth.email.response.PostCodeToEmailResponse
import com.uou.capstone.api.auth.email.response.PostSignUpWithEmailResponse
import com.uou.capstone.api.auth.kakao.request.PostKakaoSdkLoginReq
import com.uou.capstone.api.auth.kakao.response.PostKakaoSdkLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthInterface {
    @POST("users/auth/email/validation")
    suspend fun sendCodeToEmail(@Body postCodeToEmailReq: PostCodeToEmailReq) : PostCodeToEmailResponse

    @POST("users/auth/email")
    suspend fun signUpWithEmail(@Body postSignUpWithEmailReq: PostSignUpWithEmailReq) : PostSignUpWithEmailResponse

    // 카카오 로그인
    @POST("users/auth/kakao")
    suspend fun loginWithKakaoSdk(@Body postKakaoSdkLoginReq: PostKakaoSdkLoginReq) : PostKakaoSdkLoginResponse
}