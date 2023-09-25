package com.uou.capstone.api.auth.email.request

import com.google.gson.annotations.SerializedName

data class PostSignUpWithEmailReq(
    @SerializedName(value = "email") private var email: String?,
    @SerializedName(value = "password") private val password: String?,
    @SerializedName(value = "name") private val name: String?,
    @SerializedName(value = "nickname") private val nickname: String?
)
