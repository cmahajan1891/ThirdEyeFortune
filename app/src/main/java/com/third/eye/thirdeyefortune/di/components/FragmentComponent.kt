package com.third.eye.thirdeyefortune.di.components

import com.third.eye.thirdeyefortune.di.modules.FragmentModule
import com.third.eye.thirdeyefortune.di.qualifiers.FragmentScope
import com.third.eye.thirdeyefortune.ui.main.fragments.HomeFragment
import com.third.eye.thirdeyefortune.ui.main.fragments.LoginFragment
import com.third.eye.thirdeyefortune.ui.main.fragments.SignUpFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {
    fun inject(fragment: LoginFragment)
    fun inject(fragment: SignUpFragment)
    fun inject(fragment: HomeFragment)
}