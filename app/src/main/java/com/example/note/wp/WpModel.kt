package com.example.note.wp

import android.content.Context
import android.preference.PreferenceManager
import org.koin.java.KoinJavaComponent

//单例函数，用到module的地方会内嵌
inline val wpModel: WpModel
    get() = KoinJavaComponent.getKoin().get()

class WpModel(
    val context: Context
) {

    companion object {
        const val KEY = "wallpaper_key"
        const val INVILD_VALUE = -1
    }
    //继承baseactivity的都有监听接口，监听壁纸变化
    val wpListener = mutableListOf<Wallpaper>()


    //壁纸数据（key+int）存入全局sp
    fun putSp(
        key: String,
        value: Int
    ) {
        PreferenceManager.getDefaultSharedPreferences(
            context.applicationContext
        ).edit().apply {
           putInt(key, value)
            apply()
        }

        //对每个有监听接口的页面，都改变壁纸，listener的size不为0

        for (listener in wpListener) {
            listener.onWallpaperChanged(value)
        }
    }

    fun getSp(
        key: String
    ): Int = PreferenceManager.getDefaultSharedPreferences(context.applicationContext).getInt(key, INVILD_VALUE)
}