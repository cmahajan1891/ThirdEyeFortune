package com.third.eye.thirdeyefortune.ui.main.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.third.eye.thirdeyefortune.R
import com.third.eye.thirdeyefortune.databinding.HomeFragmentBinding
import com.third.eye.thirdeyefortune.di.components.FragmentComponent
import com.third.eye.thirdeyefortune.ui.base.BaseFragment
import com.third.eye.thirdeyefortune.ui.main.viewModels.HomeFragmentViewModel
import com.third.eye.thirdeyefortune.utils.common.Event

class HomeFragment : BaseFragment<HomeFragmentViewModel, HomeFragmentBinding>() {

    companion object {
        const val TAG = "HomeFragment"
        fun newInstance() = HomeFragment()
    }

    override fun setupView(view: View, savedInstanceState: Bundle?) {
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun getLayoutResourceId(): Int = R.layout.home_fragment

    override fun setupObservers() {
        super.setupObservers()
        // Event is used by the view model to tell the activity to launch another activity
        // view model also provided the Bundle in the event that is needed for the Activity
        viewModel.launchLogin.observe(this, Observer<Event<Map<String, String>>> {
            it.getIfNotHandled()?.run {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNavigation())
            }
        })

        viewModel.username.observe(this, Observer {
            binding.username.text = it
        })

    }
}