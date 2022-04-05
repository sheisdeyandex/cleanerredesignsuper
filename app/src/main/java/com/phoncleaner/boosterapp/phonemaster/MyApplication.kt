package com.phoncleaner.boosterapp.phonemaster

import android.app.Activity
import android.app.Application
import android.graphics.drawable.Drawable
import com.github.terrakok.cicerone.Cicerone
import com.onesignal.OneSignal
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig

class MyApplication : Application() {
     val ONESIGNAL_APP_ID = "9eb959a0-e7b9-4299-8e1e-1ee9b7250465"
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()
    override fun onCreate() {
        super.onCreate()
        sInstance = this
        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        activateAppMetrica()

    }
    companion object {
        lateinit var sInstance: MyApplication
            private set
        fun getInstance(): MyApplication {
            return sInstance
        }
        fun getInstance(activity: Activity): MyApplication {
            return activity.application as MyApplication
        }
        @JvmField
        var worktime: String = ""
        var finalUltraModeUsageTime: String = ""
        var finalExtremeModeUsageTime: String = ""
        var optimize:Boolean= false
        var energysaving:Boolean= false
        var coolcpu:Boolean= false
        var clean:Boolean= false
        var others:Boolean= false
        var notificationClicked:Boolean= false
        var premiumUser: Boolean = false
        var showuserpolicy: Boolean = false
        var bluetoothPermissionGranted: Boolean = false
        var animImageDrawable: Drawable? = null
        var animImageDrawableSecond: Drawable? = null
        var animImageDrawableThird: Drawable? = null
        var animImageDrawablefourth: Drawable? = null
        var animImageDrawableFifth: Drawable? = null
    }
    private fun activateAppMetrica() {
        val appMetricaConfig: YandexMetricaConfig =
            YandexMetricaConfig.newConfigBuilder("050e8ec2-5e6c-4be9-9029-5a81b54d6342")
                .withLocationTracking(true)
                .withStatisticsSending(true)
                .build()
        YandexMetrica.activate(applicationContext, appMetricaConfig)
    }

    private fun isFirstActivationAsUpdate(): Boolean {

    return true}
}