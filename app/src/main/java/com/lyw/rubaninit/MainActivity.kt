package com.lyw.rubaninit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lyw.rubaninit.test.TestManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TestManager.test()
    }
}
