package com.uou.capstone.api.auth

import com.uou.capstone.api.auth.email.request.PostCodeToEmailReq
import com.uou.capstone.api.auth.email.request.PostEmailLoginReq
import com.uou.capstone.api.auth.email.request.PostSignUpWithEmailReq
import com.uou.capstone.api.auth.email.response.PostCodeToEmailResponse
import com.uou.capstone.api.auth.email.response.PostEmailLoginResponse
import com.uou.capstone.api.auth.email.response.PostSignUpWithEmailResponse
import com.uou.capstone.api.auth.google.request.PostGoogleSdkLoginReq
import com.uou.capstone.api.auth.google.response.PostGoogleSdkLoginResponse
import com.uou.capstone.api.auth.kakao.request.PostKakaoSdkLoginReq
import com.uou.capstone.api.auth.kakao.response.PostKakaoSdkLoginResponse
import com.uou.capstone.api.getRetrofit

class AuthService {
    private val api: AuthInterface = getRetrofit().create(AuthInterface::class.java)

    // 이메일 인증코드 전송
    suspend fun sendCodeToEmail(req: PostCodeToEmailReq): PostCodeToEmailResponse {
        return api.sendCodeToEmail(req)
    }

    // 이메일로 회원가입
    suspend fun signUpWithEmail(req: PostSignUpWithEmailReq) : PostSignUpWithEmailResponse {
        return api.signUpWithEmail(req)
    }

    // 구글 로그인
    suspend fun loginWithGoogleSdk(req: PostGoogleSdkLoginReq) : PostGoogleSdkLoginResponse {
        return api.loginWithGoogleSdk(req);
    }
    
    // 카카오 로그인
    suspend fun loginWithKakaoSdk(req: PostKakaoSdkLoginReq) : PostKakaoSdkLoginResponse {
        return api.loginWithKakaoSdk(req)
    }

    // 이메일 로그인
    suspend fun loginWithEmail(req: PostEmailLoginReq) : PostEmailLoginResponse{
        return api.loginWithEmail(req)
    }
}