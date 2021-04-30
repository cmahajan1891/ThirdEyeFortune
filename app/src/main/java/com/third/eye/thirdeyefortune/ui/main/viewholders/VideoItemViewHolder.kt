package com.third.eye.thirdeyefortune.ui.main.viewholders

import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.lifecycle.Observer
import com.third.eye.thirdeyefortune.R
import com.third.eye.thirdeyefortune.data.model.Video
import com.third.eye.thirdeyefortune.di.components.ViewHolderComponent
import com.third.eye.thirdeyefortune.ui.base.BaseItemViewHolder
import com.third.eye.thirdeyefortune.ui.main.viewModels.VideoItemViewModel
import kotlinx.android.synthetic.main.item_view_videos.view.*

class VideoItemViewHolder(parent: ViewGroup) :
    BaseItemViewHolder<Video, VideoItemViewModel>(R.layout.item_view_videos, parent) {

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) {
        viewHolderComponent.inject(this)
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.uri.observe(this, Observer {
            itemView.video_view.setVideoURI(it)
            itemView.video_view.setMediaController(MediaController(itemView.context))
            itemView.video_view.requestFocus()
            itemView.video_view.start()
            itemView.video_view.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = true
            }
        })

    }

    override fun setupView(view: View) {
        view.setOnClickListener {
            viewModel.onItemClick(adapterPosition)
        }
    }
}
