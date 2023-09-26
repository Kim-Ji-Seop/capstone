package com.uou.capstone.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.uou.capstone.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var googleSignInClient: GoogleSignInClient
    private val signInResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginCreateBtn.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        // Kakao 로그인
        // --------------------
        // Google 로그인
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // Google 로그인 버튼
        binding.loginGoogleBtn.setOnClickListener {
            signIn()
        }
        // --------------------
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    // Kakao 로그인
    // --------------------

    // Google 로그인
    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        signInResultLauncher.launch(signInIntent) // 이 부분이 변경됨
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // TODO: 구글 로그인이 성공하면 가져온 account 정보를 사용하여 내 서버로 사용자 정보를 전송합니다.

            updateUI(account)
        } catch (e: ApiException) {
            // Google 로그인에 실패했을 때의 처리
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            // TODO: 사용자가 이미 로그인한 상태, 필요한 UI 업데이트나 다음 활동 시작 등의 작업을 수행
            Log.i("Google Login","이미 로그인된 상태")
            Log.i("사용자정보-성명 :",account.displayName.toString())
            Log.i("사용자정보-이메일 :",account.email.toString())
            Log.i("사용자정보-프로필 :",account.photoUrl.toString())

        } else {
            // TODO: 사용자가 로그아웃 상태이거나 로그인하지 않았습니다. 로그인 버튼을 표시하도록 UI를 업데이트
            Log.d("Google Login","로그아웃된 상태")
        }
    }
    // --------------------
}