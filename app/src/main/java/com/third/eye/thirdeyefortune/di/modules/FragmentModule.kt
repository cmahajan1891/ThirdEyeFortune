package com.third.eye.thirdeyefortune.di.modules

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.third.eye.thirdeyefortune.data.repository.VideoRepository
import com.third.eye.thirdeyefortune.network.NetworkHelper
import com.third.eye.thirdeyefortune.rx.SchedulerProvider
import com.third.eye.thirdeyefortune.ui.base.BaseFragment
import com.third.eye.thirdeyefortune.ui.main.adapters.VideosAdapter
import com.third.eye.thirdeyefortune.ui.main.viewModels.*
import com.third.eye.thirdeyefortune.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class FragmentModule(private val fragment: BaseFragment<*, *>) {

    @Provides
    fun providesFeedsFragmentViewModel(
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        schedulerProvider: SchedulerProvider,
        mAuth: FirebaseAuth,
        videoRepository: VideoRepository
    ): FeedsFragmentViewModel =
        ViewModelProviders.of(fragment, ViewModelProviderFactory(FeedsFragmentViewModel::class) {
            FeedsFragmentViewModel(
                compositeDisposable = compositeDisposable,
                networkHelper = networkHelper,
                schedulerProvider = schedulerProvider,
                mAuth = mAuth,
                videoRepository = videoRepository
            )
        }).get(FeedsFragmentViewModel::class.java)

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
    fun providesProfileFragmentViewModel(
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        schedulerProvider: SchedulerProvider
    ): ProfileFragmentViewModel =
        ViewModelProviders.of(fragment, ViewModelProviderFactory(ProfileFragmentViewModel::class) {
            ProfileFragmentViewModel(
                compositeDisposable = compositeDisposable,
                networkHelper = networkHelper,
                schedulerProvider = schedulerProvider
            )
        }).get(ProfileFragmentViewModel::class.java)

    @Provides
    fun providesCreateChallengeFragmentViewModel(
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        schedulerProvider: SchedulerProvider
    ): CreateChallengeFragmentViewModel =
        ViewModelProviders.of(
            fragment,
            ViewModelProviderFactory(CreateChallengeFragmentViewModel::class) {
                CreateChallengeFragmentViewModel(
                    compositeDisposable = compositeDisposable,
                    networkHelper = networkHelper,
                    schedulerProvider = schedulerProvider
                )
            }).get(CreateChallengeFragmentViewModel::class.java)

    @Provides
    fun provideLinearLayoutManager(): RecyclerView.LayoutManager =
        LinearLayoutManager(fragment.context)

    @Provides
    fun provideVideosAdapter() = VideosAdapter(fragment.lifecycle, ArrayList())

}
