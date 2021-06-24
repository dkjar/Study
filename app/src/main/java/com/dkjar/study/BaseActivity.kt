package com.dkjar.study

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dkjar.study.common.DateUtils
import com.dkjar.study.common.LogUtils

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