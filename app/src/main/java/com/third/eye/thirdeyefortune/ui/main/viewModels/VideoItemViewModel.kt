package com.third.eye.thirdeyefortune.ui.main.viewModels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.third.eye.thirdeyefortune.data.model.Video
import com.third.eye.thirdeyefortune.log.Logger
import com.third.eye.thirdeyefortune.network.NetworkHelper
import com.third.eye.thirdeyefortune.rx.SchedulerProvider
import com.third.eye.thirdeyefortune.ui.base.BaseItemViewModel
import com.third.eye.thirdeyefortune.utils.common.Resource
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class VideoItemViewModel @Inject constructor(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseItemViewModel<Video>(schedulerProvider, compositeDisposable, networkHelper) {

    companion object {
        const val TAG = "VideoItemViewModel"
    }

    val uri: LiveData<Uri?> = Transformations.map(data) { it.uri }

    fun onItemClick(position: Int) {
        messageString.postValue(Resource.success("onItemClick at $position"))
        Logger.d(TAG, "onItemClick at $position")
    }

    override fun onCreate() {
        Logger.d(TAG, "onCreate called")
    }
}
