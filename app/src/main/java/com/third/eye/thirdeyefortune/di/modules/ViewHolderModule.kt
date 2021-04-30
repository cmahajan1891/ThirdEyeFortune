package com.third.eye.thirdeyefortune.di.modules

import androidx.lifecycle.LifecycleRegistry
import com.third.eye.thirdeyefortune.di.qualifiers.ViewModelScope
import com.third.eye.thirdeyefortune.ui.base.BaseItemViewHolder
import dagger.Module
import dagger.Provides

@Module
class ViewHolderModule(private val viewHolder: BaseItemViewHolder<*, *>) {

    @Provides
    @ViewModelScope
    fun provideLifecycleRegistry(): LifecycleRegistry = LifecycleRegistry(viewHolder)
}
