package com.uou.capstone.api.auth

import com.uou.capstone.api.auth.email.request.PostCodeToEmailReq
import com.uou.capstone.api.auth.email.request.PostSignUpWithEmailReq
import com.uou.capstone.api.auth.email.response.PostCodeToEmailResponse
import com.uou.capstone.api.auth.email.response.PostSignUpWithEmailResponse
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
}