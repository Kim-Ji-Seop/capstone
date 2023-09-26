package com.uou.capstone.api.auth.google.response.result

import com.google.gson.annotations.SerializedName

data class PostGoogleSdkLoginResult(
    @SerializedName(value = "tokenDto") private val tokenDto: String,
    @SerializedName(value = "userIdx") private val userIdx: Long,
    @SerializedName(value = "nickname") private val nickname: String
)
