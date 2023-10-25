package com.uou.capstone.api.auth.email.response

import com.google.gson.annotations.SerializedName
import com.uou.capstone.api.auth.email.response.result.PostEmailLoginResult

data class PostEmailLoginResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("httpStatus") val httpStatus: String,
    @SerializedName("result") val result: PostEmailLoginResult?
)
