package com.third.eye.thirdeyefortune.ui.main.viewModels

import com.third.eye.thirdeyefortune.network.NetworkHelper
import com.third.eye.thirdeyefortune.rx.SchedulerProvider
import com.third.eye.thirdeyefortune.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class ProfileFragmentViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseViewModel(
    schedulerProvider = schedulerProvider,
    compositeDisposable = compositeDisposable,
    networkHelper = networkHelper
) {
    override fun onCreate() {

    }

}
