package com.uou.capstone.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.uou.capstone.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        moveLogin(1)
    }

    private fun moveLogin(sec: Int) {
        // 일정 시간 후에 실행되는 코드 블록
        Handler(Looper.getMainLooper()).postDelayed({
            // LoginActivity로 이동하는 인텐트 생성
            val intent = Intent(applicationContext, LoginActivity::class.java)
            //intent.putExtra("permission", true) // 인텐트에 추가 정보 전달
            startActivity(intent) // LoginActivity 시작
            finish() // 현재 액티비티 종료
        }, 1000L * sec) // 일정 시간(초 단위) 지연 후 실행
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}