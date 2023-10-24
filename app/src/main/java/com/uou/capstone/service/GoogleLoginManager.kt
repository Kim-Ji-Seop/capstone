package com.uou.capstone.service

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.uou.capstone.activity.LoginActivity
import com.uou.capstone.activity.MainActivity
import com.uou.capstone.api.auth.AuthService
import com.uou.capstone.api.auth.google.request.PostGoogleSdkLoginReq
import com.uou.capstone.common.SharedPreferenceManger
import kotlinx.coroutines.launch
import retrofit2.HttpException

class GoogleLoginManager(private val activity: LoginActivity, private val authService: AuthService, private val lifecycleScope: LifecycleCoroutineScope) {

    private lateinit var googleSignInClient: GoogleSignInClient

    private val signInResultLauncher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }
    }

    fun initializeGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    // Google 로그인 화면으로 이동한다
    fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        signInResultLauncher.launch(signInIntent)
    }

    // signInResultLauncher로부터 받은 결과를 처리
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            updateUI(account) // 로그인 성공
        } catch (e: ApiException) {
            updateUI(null) // 로그인 실패
        }
    }

    // 이전에 로그인한 적이 있는지 확인
    fun checkExistingLogin() {
        val account = GoogleSignIn.getLastSignedInAccount(activity)
        updateUI(account)
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            Log.i("사용자정보-성명 :",account.displayName.toString())
            Log.i("사용자정보-이메일 :",account.email.toString())
            Log.i("사용자정보-프로필 :",account.photoUrl.toString())
            val req = PostGoogleSdkLoginReq(
                profileImg = account.photoUrl.toString(),
                email = account.email.toString(),
                nickname = account.displayName.toString(),
                provider = "GOOGLE")
            // 코루틴 (비동기)
            lifecycleScope.launch {
                try {
                    val response = authService.loginWithGoogleSdk(req)
                    when (response.code) {
                        200 -> {
                            response.result?.let { result ->
                                Log.d(LoginActivity.LOG_TAG, "Result : $result")
                                SharedPreferenceManger.setLoginInfo(
                                    activity,
                                    result.getTokenDto().getAccessToken(),
                                    result.getTokenDto().getRefreshToken(),
                                    result.getUserIdx(),
                                    result.getNickname(),
                                    "GOOGLE"
                                )

                                // 액티비티 이동
                                val intent = Intent(activity, MainActivity::class.java)
                                activity.startActivity(intent)
                                activity.finish()
                            }
                        }
                    }
                } catch (e: HttpException) {

                }

            }
        } else {
            // TODO: 사용자가 로그아웃 상태이거나 로그인하지 않았습니다. 로그인 버튼을 표시하도록 UI를 업데이트
            Log.d("Google Login","로그아웃된 상태")
        }
    }

    private fun autoLoginGoogle(){

    }

}
