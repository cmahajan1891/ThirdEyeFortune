package com.third.eye.thirdeyefortune

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.third.eye.thirdeyefortune.di.components.ApplicationComponent
import com.third.eye.thirdeyefortune.di.components.DaggerApplicationComponent
import com.third.eye.thirdeyefortune.di.modules.ApplicationModule
import com.third.eye.thirdeyefortune.log.Logger

class ThirdEyeFortuneApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    companion object {
        private const val TAG = "FortuneApplication"
    }

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
        Logger.d(TAG, "dependencies injected")
    }
}
