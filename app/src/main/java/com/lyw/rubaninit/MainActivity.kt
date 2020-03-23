package com.lyw.rubaninit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lyw.rubaninit.test.TestManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TestManager.test(application, BuildConfig.DEBUG)
    }
}
