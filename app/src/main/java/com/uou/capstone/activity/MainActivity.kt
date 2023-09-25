package com.uou.capstone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uou.capstone.R
import com.uou.capstone.databinding.ActivityMainBinding
import com.uou.capstone.fragment.HomeFragment
import com.uou.capstone.fragment.InfoFragment
import com.uou.capstone.fragment.MatchFragment
import com.uou.capstone.fragment.RecordFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation() // 바텀 네비

    }

    // 바텀 네비게이션 뷰 초기화
    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm_js, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnvJs.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm_js, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.matchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm_js, MatchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.recordFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm_js, RecordFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.infoFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm_js, InfoFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}