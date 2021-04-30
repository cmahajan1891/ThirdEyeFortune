package com.third.eye.thirdeyefortune.di.modules

import androidx.lifecycle.ViewModelProviders
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.third.eye.thirdeyefortune.data.repository.VideoRepository
import com.third.eye.thirdeyefortune.network.NetworkHelper
import com.third.eye.thirdeyefortune.rx.SchedulerProvider
import com.third.eye.thirdeyefortune.ui.base.BaseActivity
import com.third.eye.thirdeyefortune.ui.main.viewModels.FeedsFragmentViewModel
import com.third.eye.thirdeyefortune.ui.main.viewModels.LoginFragmentViewModel
import com.third.eye.thirdeyefortune.ui.main.viewModels.ThirdEyeFortuneActivityViewModel
import com.third.eye.thirdeyefortune.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Kotlin Generics Reference: https://kotlinlang.org/docs/reference/generics.html
 * Basically it means that we can pass any class that extends BaseActivity which take
 * BaseViewModel subclass as parameter
 */
@Module
class ActivityModule(private val activity: BaseActivity<*, *>) {

    @Provides
    fun providesThirdEyeFortuneActivityViewModel(
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        schedulerProvider: SchedulerProvider
    ): ThirdEyeFortuneActivityViewModel =
        ViewModelProviders.of(
            activity,
            ViewModelProviderFactory(ThirdEyeFortuneActivityViewModel::class) {
                ThirdEyeFortuneActivityViewModel(
                    compositeDisposable = compositeDisposable,
                    networkHelper = networkHelper,
                    schedulerProvider = schedulerProvider
                )
            }).get(ThirdEyeFortuneActivityViewModel::class.java)
}
