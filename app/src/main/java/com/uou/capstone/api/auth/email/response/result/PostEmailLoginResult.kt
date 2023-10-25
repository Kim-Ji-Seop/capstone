package com.uou.capstone.api.auth.email.response.result

import com.google.gson.annotations.SerializedName
import com.uou.capstone.api.auth.TokenDto

data class PostEmailLoginResult(
    @SerializedName(value = "tokenDto") private val tokenDto: TokenDto,
    @SerializedName(value = "userIdx") private val userIdx: Long,
    @SerializedName(value = "nickname") private val nickname: String
){
    fun getTokenDto() = tokenDto
    fun getUserIdx() = userIdx
    fun getNickname() = nickname
}
