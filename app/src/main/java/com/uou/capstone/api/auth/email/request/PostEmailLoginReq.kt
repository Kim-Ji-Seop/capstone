package com.uou.capstone.api.auth.email.request

import com.google.gson.annotations.SerializedName

data class PostEmailLoginReq(
    @SerializedName(value = "email") private var email: String?,
    @SerializedName(value = "password") private var password: String?,
    @SerializedName(value = "provider") private var provider: String
)