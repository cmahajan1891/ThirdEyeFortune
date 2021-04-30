package com.third.eye.thirdeyefortune.ui.main.viewModels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.firebase.auth.FirebaseAuth
import com.third.eye.thirdeyefortune.data.model.Video
import com.third.eye.thirdeyefortune.data.repository.VideoRepository
import com.third.eye.thirdeyefortune.network.NetworkHelper
import com.third.eye.thirdeyefortune.rx.SchedulerProvider
import com.third.eye.thirdeyefortune.ui.base.BaseViewModel
import com.third.eye.thirdeyefortune.utils.common.Event
import com.third.eye.thirdeyefortune.utils.common.Resource
import com.third.eye.thirdeyefortune.utils.common.Status
import io.reactivex.disposables.CompositeDisposable

class FeedsFragmentViewModel(
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    schedulerProvider: SchedulerProvider,
    private val mAuth: FirebaseAuth,
    videoRepository: VideoRepository
) : BaseViewModel(
    compositeDisposable = compositeDisposable,
    networkHelper = networkHelper,
    schedulerProvider = schedulerProvider
) {

    val launchLogin: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()
    val username: MutableLiveData<String> = MutableLiveData()
    private val photoUrl: MutableLiveData<Uri?> = MutableLiveData()

    override fun onCreate() {
        // updateUi()
    }

    private fun updateUi() {
        val currentUser = mAuth.currentUser

        when {
            currentUser != null -> {
//                photoUrl.postValue(currentUser.photoUrl)
//                username.postValue(currentUser.email)
            }
            else -> {
                launchLogin.postValue(Event(emptyMap()))
            }
        }
    }

    fun updateUserInfo() {
        updateUi()
    }

}
