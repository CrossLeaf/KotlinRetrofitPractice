package com.eton.kotlinretrofitpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eton.kotlinretrofitpractice.ui.main.MainFragment
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}