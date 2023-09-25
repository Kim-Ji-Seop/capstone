package com.uou.capstone.api.auth

import com.uou.capstone.api.auth.email.request.PostCodeToEmailReq
import com.uou.capstone.api.auth.email.response.PostCodeToEmailResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthInterface {
    @POST("users/auth/email/validation")
    suspend fun sendCodeToEmail(@Body postCodeToEmailReq: PostCodeToEmailReq) : PostCodeToEmailResponse
}