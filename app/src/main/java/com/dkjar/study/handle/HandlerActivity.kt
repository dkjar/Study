package com.dkjar.study.handle

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import com.dkjar.study.BaseActivity
import com.dkjar.study.R
import com.dkjar.study.common.LogUtils
import kotlinx.android.synthetic.main.activity_handler.*
import java.lang.ref.WeakReference

class HandlerActivity :  BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        this.TAG = LogUtils.HANDLER
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler)
        LogUtils.v(TAG, "$this onCreate ")

        backBtn.setOnClickListener {
            this.finish()
        }


        //主线程handler
        mainHandler()

        //子线程handler
        threadHandler()
    }

    private var mainHandler : Handler? = null

    fun click(v: View){
        LogUtils.v(TAG, "click fun")
        if (this == null) {
            LogUtils.v(TAG, "HandlerActivity is null")
        }else {
            LogUtils.v(TAG, "HandlerActivity is not null")
        }
    }

    fun mainHandler() {
        //hander 默认加到 main looper 中
        mainHandler = object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                LogUtils.v(TAG, "mainHandler handleMessage")
                val ac = WeakReference<HandlerActivity>(this@HandlerActivity)
                Thread {
                   val activity = ac.get()
                    activity?.click(backBtn)
                    Thread.sleep(10000) //模拟耗时任务
                    activity?.click(backBtn)
                }.start()

                click(backBtn)
            }
        }


        // Handler() 方法已不推荐使用，使用不好就会有内存泄漏
        val loaderHandler: Handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                LogUtils.v(TAG, "loaderHandler handleMessage")
            }
        }

        // Message() 使用方式不推荐使用，
        val msg1 = Message()
        msg1.what = 100
        loaderHandler.sendMessage(msg1)

        // Message.obtain() 推荐使用，
        /**
         * Return a new Message instance from the global pool. Allows us to
         * avoid allocating new objects in many cases.
         */
        val msg2 = Message.obtain()
        msg2.what = 110
        loaderHandler.sendMessage(msg2)

        // msg 不能重复使用。
        val msg3 = Message.obtain()
        msg3.what = 120
        mainHandler?.sendMessage(msg3)
    }


    fun threadHandler(){
        //handler 不放主线程中，不能收到消息.
        Thread {

            //Can't create handler inside thread that has not called Looper.prepare()
            Looper.prepare() //如果不加这个

            val threadHandler: Handler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    LogUtils.v(TAG, "threadHandler handleMessage")

                    Looper.myLooper()?.quitSafely() //需要退出，不然会一直有looper 存在
                }
            }

            Looper.loop()
            threadHandler.sendMessage(Message.obtain())


        }.start()
    }

}