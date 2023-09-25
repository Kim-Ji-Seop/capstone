package com.uou.capstone.api.auth.email.request

import com.google.gson.annotations.SerializedName

data class PostCodeToEmailReq(
    @SerializedName("email") var email: String
)