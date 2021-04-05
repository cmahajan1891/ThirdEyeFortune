package com.third.eye.thirdeyefortune.di.components

import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.third.eye.thirdeyefortune.ThirdEyeFortuneApplication
import com.third.eye.thirdeyefortune.di.modules.ApplicationModule
import com.third.eye.thirdeyefortune.network.NetworkHelper
import com.third.eye.thirdeyefortune.rx.SchedulerProvider
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: ThirdEyeFortuneApplication)
    fun getCompositeDisposable(): CompositeDisposable
    fun getNetworkHelper(): NetworkHelper
    fun getSchedulerProvider(): SchedulerProvider
    fun getFirebaseAuth(): FirebaseAuth
    fun getFacebookCallBackManager(): CallbackManager
    fun getLoginManager(): LoginManager
}
