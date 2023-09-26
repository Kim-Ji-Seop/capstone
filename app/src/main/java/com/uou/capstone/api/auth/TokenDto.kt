package com.uou.capstone.api.auth

import com.google.gson.annotations.SerializedName

data class TokenDto(
    @SerializedName(value = "grantType") private val grantType: String,
    @SerializedName(value = "accessToken") private val accessToken: String,
    @SerializedName(value = "refreshToken") private val refreshToken: String
)
