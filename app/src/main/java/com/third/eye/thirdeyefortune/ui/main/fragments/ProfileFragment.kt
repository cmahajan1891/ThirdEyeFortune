package com.third.eye.thirdeyefortune.ui.main.fragments

import android.os.Bundle
import android.view.View
import com.third.eye.thirdeyefortune.R
import com.third.eye.thirdeyefortune.databinding.ProfileFragmentBinding
import com.third.eye.thirdeyefortune.di.components.FragmentComponent
import com.third.eye.thirdeyefortune.ui.base.BaseFragment
import com.third.eye.thirdeyefortune.ui.main.viewModels.ProfileFragmentViewModel

class ProfileFragment : BaseFragment<ProfileFragmentViewModel, ProfileFragmentBinding>() {

    companion object {
        const val TAG = "ProfileFragment"
        fun newInstance() = ProfileFragment()
    }

    override fun setupView(view: View, savedInstanceState: Bundle?) {
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun getLayoutResourceId(): Int = R.layout.profile_fragment

}
