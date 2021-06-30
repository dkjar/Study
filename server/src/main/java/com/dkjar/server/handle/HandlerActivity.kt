package com.dkjar.server.handle

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import com.dkjar.server.BaseActivity
import com.dkjar.server.R
import com.dkjar.server.common.LogUtils
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

    class MyThread : Thread() {
        override fun run() {
            LogUtils.v(LogUtils.HANDLER, "MyThread")
            //Can't create handler inside thread that has not called Looper.prepare()
            Looper.prepare() //如果不加这个

            val threadHandler: Handler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    LogUtils.v(LogUtils.HANDLER, "threadHandler handleMessage")

                    Looper.myLooper()?.quitSafely() //需要退出，不然会一直有looper 存在
                }
            }

            val msg = Message.obtain()
            msg.what = 1000
            threadHandler.sendMessage(msg)

            Looper.loop()
            //需要等到循环结束才到执行这一行
            LogUtils.v(LogUtils.HANDLER, " if you want run to this line, please wait loop end ")

            //anr 1. 输入事件5s以上没有响应，2.broadcastreceiver 在 10s 内没有执行完毕.

        }
    }

    fun threadHandler(){

        //handler 不放主线程中，不能收到消息.
        MyThread().start()

        Thread {

            val thread2Handler: Handler = object : Handler(Looper.getMainLooper()) {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    LogUtils.v(LogUtils.HANDLER, "thread2Handler handleMessage")
                }
            }

            thread2Handler.sendMessage(Message.obtain())
        }.start()
    }

}