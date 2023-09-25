package com.uou.capstone.api.auth.email.response.result

import com.google.gson.annotations.SerializedName

data class PostSignUpWithEmailResult(
    @SerializedName(value = "userIdx") private val userIdx: Long?
){
    fun getUserIdx() = userIdx
}

