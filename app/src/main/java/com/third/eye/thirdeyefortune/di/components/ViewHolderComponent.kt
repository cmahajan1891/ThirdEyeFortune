package com.third.eye.thirdeyefortune.di.components

import com.third.eye.thirdeyefortune.di.modules.ViewHolderModule
import com.third.eye.thirdeyefortune.di.qualifiers.ViewModelScope
import com.third.eye.thirdeyefortune.ui.main.viewholders.VideoItemViewHolder
import dagger.Component

@ViewModelScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ViewHolderModule::class]
)
interface ViewHolderComponent {

    fun inject(viewHolder: VideoItemViewHolder)
}
