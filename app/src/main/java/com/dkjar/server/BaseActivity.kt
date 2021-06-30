package com.dkjar.server

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dkjar.server.common.LogUtils

open class BaseActivity : AppCompatActivity() {

    var TAG = "BaseActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.v(TAG, "$this onCreate ")

    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.v(TAG, "$this onDestroy ")


    }
}