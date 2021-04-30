package com.third.eye.thirdeyefortune.ui.main.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.third.eye.thirdeyefortune.data.model.Video
import com.third.eye.thirdeyefortune.network.NetworkHelper
import com.third.eye.thirdeyefortune.rx.SchedulerProvider
import com.third.eye.thirdeyefortune.ui.base.BaseViewModel
import com.third.eye.thirdeyefortune.utils.common.Resource
import com.third.eye.thirdeyefortune.utils.common.Status
import io.reactivex.disposables.CompositeDisposable

class ThirdEyeFortuneActivityViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseViewModel(
    schedulerProvider = schedulerProvider,
    compositeDisposable = compositeDisposable,
    networkHelper = networkHelper
) {

    val addVideo: MutableLiveData<Video> = MutableLiveData()
    private val videoLiveData: MutableLiveData<Resource<List<Video>>> = MutableLiveData()

    override fun onCreate() {
        if (videoLiveData.value == null && checkInternetConnectionWithMessage()) {
//            videoLiveData.postValue(Resource.loading())
//            compositeDisposable.add(
//                videoRepository.fetchDummy("MY_SAMPLE_DUMMY")
//                    .subscribeOn(schedulerProvider.io())
//                    .subscribe(
//                        { videoLiveData.postValue(Resource.success(it)) },
//                        {
//                            handleNetworkError(it)
//                            videoLiveData.postValue(Resource.error())
//                        })
//            )
        }
    }

    fun getVideos(): LiveData<List<Video>> =
        Transformations.map(videoLiveData) { it.data }

    fun isVideosFetching(): LiveData<Boolean> =
        Transformations.map(videoLiveData) { it.status == Status.LOADING }

    fun addVideosData(video: Video) {
        addVideo.postValue(video)
    }

}
