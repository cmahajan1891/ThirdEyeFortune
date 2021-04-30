package com.third.eye.thirdeyefortune.data.repository

import com.third.eye.thirdeyefortune.data.remote.NetworkService
import javax.inject.Inject

class VideoRepository @Inject constructor(
    private val networkService: NetworkService
) {

//    fun fetchDummy(id: String): Single<List<Video>> =
//        networkService.doDummyCall(DummyRequest(id)).map { it.data }

}