package com.third.eye.thirdeyefortune.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.third.eye.thirdeyefortune.R
import com.third.eye.thirdeyefortune.databinding.FeedsFragmentBinding
import com.third.eye.thirdeyefortune.di.components.FragmentComponent
import com.third.eye.thirdeyefortune.ui.base.BaseFragment
import com.third.eye.thirdeyefortune.ui.main.activities.ThirdEyeFortuneActivity
import com.third.eye.thirdeyefortune.ui.main.adapters.VideosAdapter
import com.third.eye.thirdeyefortune.ui.main.viewModels.FeedsFragmentViewModel
import com.third.eye.thirdeyefortune.utils.common.Event
import javax.inject.Inject
import javax.inject.Provider

class FeedsFragment : BaseFragment<FeedsFragmentViewModel, FeedsFragmentBinding>() {

    @Inject
    lateinit var videosAdapter: VideosAdapter

    @Inject
    lateinit var linearLayoutManager: Provider<RecyclerView.LayoutManager>

    companion object {
        const val TAG = "FeedsFragment"
    }

    override fun setupView(view: View, savedInstanceState: Bundle?) {
        (activity as ThirdEyeFortuneActivity).showBottomNavigation()
        binding.rvVideo.layoutManager = linearLayoutManager.get()
        binding.rvVideo.adapter = videosAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        viewModel.updateUserInfo()
        return view
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun getLayoutResourceId(): Int = R.layout.feeds_fragment

    override fun setupObservers() {
        super.setupObservers()
        // Event is used by the view model to tell the activity to launch another activity
        // view model also provided the Bundle in the event that is needed for the Activity
        viewModel.launchLogin.observe(this, Observer<Event<Map<String, String>>> {
            it.getIfNotHandled()?.run {
                findNavController().navigate(FeedsFragmentDirections.actionFeedsFragmentToNavigation())
            }
        })

        (requireActivity() as ThirdEyeFortuneActivity).viewModel.getVideos().observe(this, Observer {
            it?.run { videosAdapter.appendData(this) }
        })

        (requireActivity() as ThirdEyeFortuneActivity).viewModel.addVideo.observe(this, Observer {
            it?.run { videosAdapter.appendData(listOf(it)) }
        })

//        viewModel.username.observe(this, Observer {
//            binding.username.text = it
//        })

//        viewModel.photoUrl.observe(this, Observer {
//            if (it != null) {
//                Picasso.get().load(it.path).into(binding.profilePic)
//            }
//        })

    }

}
