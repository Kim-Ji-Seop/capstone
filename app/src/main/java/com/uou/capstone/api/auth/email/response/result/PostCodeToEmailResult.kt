package com.uou.capstone.api.auth.email.response.result

import com.google.gson.annotations.SerializedName

data class PostCodeToEmailResult(
    @SerializedName(value = "authCode") val authCode : String
)
