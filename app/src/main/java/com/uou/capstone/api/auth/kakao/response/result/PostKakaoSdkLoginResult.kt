package com.uou.capstone.api.auth.kakao.response.result

import com.google.gson.annotations.SerializedName
import com.uou.capstone.api.auth.TokenDto

data class PostKakaoSdkLoginResult(
    @SerializedName(value = "tokenDto") private val tokenDto: TokenDto,
    @SerializedName(value = "userIdx") private val userIdx: Long,
    @SerializedName(value = "nickname") private val nickname: String
)