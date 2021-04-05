package com.third.eye.thirdeyefortune.di.modules

import android.content.Context
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.third.eye.thirdeyefortune.ThirdEyeFortuneApplication
import com.third.eye.thirdeyefortune.di.qualifiers.ApplicationContext
import com.third.eye.thirdeyefortune.network.NetworkHelper
import com.third.eye.thirdeyefortune.rx.RxSchedulerProvider
import com.third.eye.thirdeyefortune.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: ThirdEyeFortuneApplication) {

    @Provides
    @Singleton
    @ApplicationContext
    fun providesContext(): Context = application

    @Provides
    fun providesCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @Singleton
    fun providesNetworkHelper(): NetworkHelper = NetworkHelper(application)

    @Provides
    fun providesSchedulerProvide(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFacebookCallbackManager(): CallbackManager = CallbackManager.Factory.create()

    @Provides
    @Singleton
    fun providesLoginManager(): LoginManager = LoginManager.getInstance()

//    @Provides
//    @Singleton
//    fun provideNetworkService(): NetworkService =
//        Networking.create(
//            BuildConfig.API_KEY,
//            BuildConfig.BASE_URL,
//            application.cacheDir,
//            10 * 1024 * 1024 // 10MB
//        )

}
