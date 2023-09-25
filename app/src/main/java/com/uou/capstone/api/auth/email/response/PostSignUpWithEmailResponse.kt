package com.uou.capstone.api.auth.email.response

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName
import com.uou.capstone.api.auth.email.response.result.PostSignUpWithEmailResult

data class PostSignUpWithEmailResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("httpStatus") val httpStatus: String,
    @Nullable @SerializedName("result") val result: PostSignUpWithEmailResult?
)
