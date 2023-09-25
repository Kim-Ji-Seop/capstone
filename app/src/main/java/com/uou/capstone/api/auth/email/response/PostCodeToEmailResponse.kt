package com.uou.capstone.api.auth.email.response

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName
import com.uou.capstone.api.auth.email.response.result.PostCodeToEmailResult

data class PostCodeToEmailResponse(
    @SerializedName("code") var code: Int,
    @SerializedName("message") var message: String,
    @SerializedName("httpStatus") var httpStatus: String,
    @Nullable @SerializedName("result") var result: PostCodeToEmailResult?
)
