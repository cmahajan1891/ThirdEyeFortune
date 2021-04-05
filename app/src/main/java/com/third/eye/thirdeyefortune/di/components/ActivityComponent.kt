package com.third.eye.thirdeyefortune.di.components

import com.third.eye.thirdeyefortune.di.modules.ActivityModule
import com.third.eye.thirdeyefortune.di.qualifiers.ActivityScope
import com.third.eye.thirdeyefortune.ui.main.activities.ThirdEyeFortuneActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {

    fun inject(activity: ThirdEyeFortuneActivity)

}