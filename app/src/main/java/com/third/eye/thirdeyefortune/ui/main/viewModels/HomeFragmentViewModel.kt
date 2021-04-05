package com.third.eye.thirdeyefortune.ui.main.viewModels

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.third.eye.thirdeyefortune.network.NetworkHelper
import com.third.eye.thirdeyefortune.rx.SchedulerProvider
import com.third.eye.thirdeyefortune.ui.base.BaseViewModel
import com.third.eye.thirdeyefortune.utils.common.Event
import io.reactivex.disposables.CompositeDisposable


class HomeFragmentViewModel(
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    schedulerProvider: SchedulerProvider,
    private val mAuth: FirebaseAuth
) : BaseViewModel(
    compositeDisposable = compositeDisposable,
    networkHelper = networkHelper,
    schedulerProvider = schedulerProvider
) {

    val launchLogin: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()
    val username: MutableLiveData<String> = MutableLiveData()

    override fun onCreate() {

        val currentUser = mAuth.currentUser

        when {
            currentUser != null -> {
                username.postValue(currentUser.email)
            }
            else -> {
                launchLogin.postValue(Event(emptyMap()))
            }
        }
    }

}