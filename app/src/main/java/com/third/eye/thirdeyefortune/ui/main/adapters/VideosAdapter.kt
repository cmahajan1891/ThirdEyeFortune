package com.third.eye.thirdeyefortune.ui.main.adapters

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.third.eye.thirdeyefortune.data.model.Video
import com.third.eye.thirdeyefortune.ui.base.BaseAdapter
import com.third.eye.thirdeyefortune.ui.main.viewholders.VideoItemViewHolder

class VideosAdapter(
    parentLifecycle: Lifecycle,
    private val videos: ArrayList<Video>
) : BaseAdapter<Video, VideoItemViewHolder>(parentLifecycle, videos) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VideoItemViewHolder(parent)
}
