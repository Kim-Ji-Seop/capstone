package com.uou.capstone.api.auth.kakao.response

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName
import com.uou.capstone.api.auth.kakao.response.result.PostKakaoSdkLoginResult

data class PostKakaoSdkLoginResponse (
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("httpStatus") val httpStatus: String,
    @Nullable @SerializedName("result") val result: PostKakaoSdkLoginResult?
)