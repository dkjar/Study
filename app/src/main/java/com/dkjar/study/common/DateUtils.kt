package com.dkjar.study.common

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {
    private val sf = SimpleDateFormat("yyyyMMdd_HHmmssSS")

    /**
     * 当前时间
     *
     * @return
     */
    val currentTime: String
        get() {
            val duration = System.currentTimeMillis()
            return String.format(
                Locale.getDefault(), "%02d:%02d:%02d",
                TimeUnit.HOURS.toHours(duration),
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
            )
        }

    /**
     * 时间戳转换成时间格式
     *
     * @param duration
     * @return
     */
    fun formatDurationTime(duration: Long): String {
        return String.format(
            Locale.getDefault(), "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(duration), TimeUnit.MILLISECONDS.toSeconds(duration)
                    - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
        )
    }

    /**
     * 根据时间戳创建文件名
     *
     * @param prefix 前缀名
     * @return
     */
    fun getCreateFileName(prefix: String): String {
        val millis = System.currentTimeMillis()
        return prefix + sf.format(millis)
    }

    /**
     * 根据时间戳创建文件名
     *
     * @return
     */
    val createFileName: String
        get() {
            val millis = System.currentTimeMillis()
            return sf.format(millis)
        }


}