package com.uou.capstone.api.auth.google.request

import com.google.gson.annotations.SerializedName

data class PostGoogleSdkLoginReq(
    @SerializedName(value = "profileImg") private var profileImg: String?,
    @SerializedName(value = "email") private var email: String,
    @SerializedName(value = "nickname") private var nickname: String,
    @SerializedName(value = "provider") private var provider: String
)
