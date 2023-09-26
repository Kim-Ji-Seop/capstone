package com.uou.capstone.api.auth

import com.uou.capstone.api.auth.email.request.PostCodeToEmailReq
import com.uou.capstone.api.auth.email.request.PostSignUpWithEmailReq
import com.uou.capstone.api.auth.email.response.PostCodeToEmailResponse
import com.uou.capstone.api.auth.email.response.PostSignUpWithEmailResponse
import com.uou.capstone.api.auth.google.request.PostGoogleSdkLoginReq
import com.uou.capstone.api.auth.google.response.PostGoogleSdkLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthInterface {
    @POST("users/auth/email/validation")
    suspend fun sendCodeToEmail(@Body postCodeToEmailReq: PostCodeToEmailReq) : PostCodeToEmailResponse

    @POST("users/auth/email")
    suspend fun signUpWithEmail(@Body postSignUpWithEmailReq: PostSignUpWithEmailReq) : PostSignUpWithEmailResponse

    // 구글 로그인
    @POST("users/auth/google")
    suspend fun loginWithGoogleSdk(@Body postGoogleSdkLoginReq: PostGoogleSdkLoginReq) : PostGoogleSdkLoginResponse
}