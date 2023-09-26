package com.uou.capstone.api.auth.google.response

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName
import com.uou.capstone.api.auth.google.response.result.PostGoogleSdkLoginResult

data class PostGoogleSdkLoginResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("httpStatus") val httpStatus: String,
    @Nullable @SerializedName("result") val result: PostGoogleSdkLoginResult?
)
