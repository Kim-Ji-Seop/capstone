package com.uou.capstone.api.auth.kakao.request

import com.google.gson.annotations.SerializedName

data class PostKakaoSdkLoginReq (
    @SerializedName(value = "profile_img") private var profileImg: String?,
    @SerializedName(value = "email") private var email: String,
    @SerializedName(value = "nickname") private var nickname: String,
    @SerializedName(value = "provider") private var provider: String
)