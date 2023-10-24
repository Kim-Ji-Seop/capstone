package com.uou.capstone.common

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceManger {
    private const val PREFERENCE_NAME = "autoLogin"

    fun getPreferences(mContext: Context): SharedPreferences{
        return mContext.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)
    }

    /**
     * SharedPreferences에서 저장된 모든 데이터를 삭제하는 메서드
     * 비동기 방식으로 데이터를 삭제
     * @param context 컨텍스트 객체
     */
    //apply -> 비동기, commit -> 동기
    fun clearPreferences(context: Context) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    /**
     * 로그인 정보를 SharedPreferences에 저장하는 메서드
     * @param context 컨텍스트 객체
     * @param jwt 저장할 JWT 토큰
     */
    fun setLoginInfo(context: Context, accessToken: String, refreshToken: String, userIdx: Long, nickname: String, provider: String) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putString("atk", accessToken)
        editor.putString("rtk", refreshToken)
        editor.putLong("userIdx",userIdx)
        editor.putString("nickname",nickname)
        editor.putString("provider",provider)
        editor.apply()
    }

    /**
     * SharedPreferences에서 저장된 로그인 정보(JWT 토큰)를 확인하는 메서드입니다.
     *
     * @param context 컨텍스트 객체
     * @return 저장된 JWT 토큰을 포함한 Map 객체
     */
    //jwt 확인
    fun getLoginJwtInfo(context: Context): Map<String, String> {
        val prefs = getPreferences(context)
        val loginInfo = mutableMapOf<String, String>()
        val jwt = prefs.getString("jwt", "") ?: ""
        loginInfo["jwt"] = jwt
        return loginInfo
    }

}