package com.example.submission3fundamental.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.submission3fundamental.R
import com.example.submission3fundamental.fragment.MyPreferenceFragment

class SettingAlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_alarm)
        supportFragmentManager.beginTransaction()
            .add(R.id.setting_alarm, MyPreferenceFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}