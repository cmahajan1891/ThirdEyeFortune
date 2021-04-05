package com.third.eye.thirdeyefortune.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.third.eye.thirdeyefortune.ThirdEyeFortuneApplication
import com.third.eye.thirdeyefortune.di.components.DaggerFragmentComponent
import com.third.eye.thirdeyefortune.di.components.FragmentComponent
import com.third.eye.thirdeyefortune.di.modules.FragmentModule
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel, B : ViewDataBinding> : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false) as B
        binding.setVariable(BR.viewModel, viewModel)
        return binding.root
    }

    protected lateinit var binding: B

    @Inject
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildFragmentComponent())
        super.onCreate(savedInstanceState)
        setupObservers()
        viewModel.onCreate()
    }

    protected open fun setupObservers() {
        viewModel.messageString.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.messageStringId.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showMessage(@StringRes messageRes: Int) = showMessage(getString(messageRes))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view, savedInstanceState)
    }

    protected abstract fun setupView(view: View, savedInstanceState: Bundle?)

    protected abstract fun injectDependencies(fragmentComponent: FragmentComponent)

    private fun buildFragmentComponent(): FragmentComponent = DaggerFragmentComponent
        .builder()
        .applicationComponent((requireContext().applicationContext as ThirdEyeFortuneApplication).applicationComponent)
        .fragmentModule(FragmentModule(this))
        .build()

    @LayoutRes
    protected abstract fun getLayoutResourceId(): Int
}
