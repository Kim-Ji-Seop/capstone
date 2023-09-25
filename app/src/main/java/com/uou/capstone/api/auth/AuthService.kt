package com.uou.capstone.api.auth

import com.uou.capstone.api.auth.email.request.PostCodeToEmailReq
import com.uou.capstone.api.auth.email.response.PostCodeToEmailResponse
import com.uou.capstone.api.getRetrofit

class AuthService {
    private val api: AuthInterface = getRetrofit().create(AuthInterface::class.java)

    suspend fun sendCodeToEmail(req: PostCodeToEmailReq): PostCodeToEmailResponse {
        return api.sendCodeToEmail(req)
    }
}