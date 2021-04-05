package com.third.eye.thirdeyefortune.di.modules

import androidx.lifecycle.ViewModelProviders
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.third.eye.thirdeyefortune.network.NetworkHelper
import com.third.eye.thirdeyefortune.rx.SchedulerProvider
import com.third.eye.thirdeyefortune.ui.base.BaseFragment
import com.third.eye.thirdeyefortune.ui.main.viewModels.HomeFragmentViewModel
import com.third.eye.thirdeyefortune.ui.main.viewModels.LoginFragmentViewModel
import com.third.eye.thirdeyefortune.ui.main.viewModels.SignUpFragmentViewModel
import com.third.eye.thirdeyefortune.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class FragmentModule(private val fragment: BaseFragment<*, *>) {

    @Provides
    fun providesLoginFragmentViewModel(
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        schedulerProvider: SchedulerProvider,
        mAuth: FirebaseAuth,
        loginManager: LoginManager,
        callbackManager: CallbackManager
    ): LoginFragmentViewModel =
        ViewModelProviders.of(fragment, ViewModelProviderFactory(LoginFragmentViewModel::class) {
            LoginFragmentViewModel(
                compositeDisposable = compositeDisposable,
                networkHelper = networkHelper,
                schedulerProvider = schedulerProvider,
                mAuth = mAuth,
                loginManager = loginManager,
                callbackManager = callbackManager
            )
        }).get(LoginFragmentViewModel::class.java)

    @Provides
    fun providesSignUpFragmentViewModel(
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        schedulerProvider: SchedulerProvider,
        mAuth: FirebaseAuth,
        loginManager: LoginManager,
        callbackManager: CallbackManager
    ): SignUpFragmentViewModel =
        ViewModelProviders.of(fragment, ViewModelProviderFactory(SignUpFragmentViewModel::class) {
            SignUpFragmentViewModel(
                compositeDisposable = compositeDisposable,
                networkHelper = networkHelper,
                schedulerProvider = schedulerProvider,
                mAuth = mAuth,
                loginManager = loginManager,
                callbackManager = callbackManager
            )
        }).get(SignUpFragmentViewModel::class.java)

    @Provides
    fun providesHomeFragmentViewModel(
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        schedulerProvider: SchedulerProvider,
        mAuth: FirebaseAuth
    ): HomeFragmentViewModel =
        ViewModelProviders.of(fragment, ViewModelProviderFactory(HomeFragmentViewModel::class) {
            HomeFragmentViewModel(
                compositeDisposable = compositeDisposable,
                networkHelper = networkHelper,
                schedulerProvider = schedulerProvider,
                mAuth = mAuth
            )
        }).get(HomeFragmentViewModel::class.java)

}
