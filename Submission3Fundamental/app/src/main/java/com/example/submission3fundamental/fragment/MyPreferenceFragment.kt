package com.example.submission3fundamental.fragment


import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.submission3fundamental.receiver.AlarmReceiver
import com.example.submission3fundamental.R

class MyPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var ALARM: String
    val repeatMessage = "Let's find popular github users"


    private lateinit var alarmPreference: SwitchPreference
    private var reminderState: Boolean = false


    companion object {
        private const val DEFAULT_VALUE_REMINDER = false
    }

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummaries()
    }

    private fun init() {
        alarmReceiver = AlarmReceiver()
        ALARM = resources.getString(R.string.setting)
        alarmPreference = findPreference<SwitchPreference>(ALARM) as SwitchPreference
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        reminderState = sh.getBoolean(ALARM, DEFAULT_VALUE_REMINDER)
        if (reminderState) {
            alarmPreference.summary = "Enabled"
        } else {
            alarmPreference.summary = "Disabled"
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {

        when (key) {
            ALARM -> {
                reminderState = sharedPreferences.getBoolean(ALARM, DEFAULT_VALUE_REMINDER)
                if (reminderState) {
                    alarmPreference.summary = resources.getString(R.string.alarm)
                    alarmReceiver.setAlarm(requireContext(), repeatMessage)
                } else {
                    alarmPreference.summary = resources.getString(R.string.cancel_alarm)
                    alarmReceiver.cancelAlarm(requireContext())
                }
            }
        }
    }
}
