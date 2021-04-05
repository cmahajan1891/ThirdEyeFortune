package com.third.eye.thirdeyefortune.ui.main.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.third.eye.thirdeyefortune.R
import com.third.eye.thirdeyefortune.databinding.SignupFragmentBinding
import com.third.eye.thirdeyefortune.di.components.FragmentComponent
import com.third.eye.thirdeyefortune.ui.base.BaseFragment
import com.third.eye.thirdeyefortune.ui.main.viewModels.SignUpFragmentViewModel
import com.third.eye.thirdeyefortune.utils.common.Event
import com.third.eye.thirdeyefortune.utils.common.Status

class SignUpFragment : BaseFragment<SignUpFragmentViewModel, SignupFragmentBinding>() {

    companion object {
        const val TAG = "SignUpFragment"
        fun newInstance() = SignUpFragment()
    }

    override fun setupView(view: View, savedInstanceState: Bundle?) {
        binding.emailText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onEmailChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onPasswordChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        binding.usernameEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onUsernameChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
    }

    override fun setupObservers() {
        super.setupObservers()
        // Event is used by the view model to tell the activity to launch another activity
        // view model also provided the Bundle in the event that is needed for the Activity
        viewModel.launchMain.observe(this, Observer<Event<Map<String, String>>> {
            it.getIfNotHandled()?.run {
                findNavController().navigate(LoginFragmentDirections.actionGlobalHomeFragment())
            }
        })

        viewModel.emailField.observe(this, Observer {
            if (binding.emailText.text.toString() != it) binding.emailText.setText(it)
        })

        viewModel.emailValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> binding.email.error = it.data?.run { getString(this) }
                else -> binding.email.isErrorEnabled = false
            }
        })

        viewModel.usernameField.observe(this, Observer {
            if (binding.usernameEditText.text.toString() != it) binding.usernameEditText.setText(it)
        })

        viewModel.usernameValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> binding.username.error = it.data?.run { getString(this) }
                else -> binding.username.isErrorEnabled = false
            }
        })

        viewModel.passwordField.observe(this, Observer {
            if (binding.passwordEditText.text.toString() != it) binding.passwordEditText.setText(it)
        })

        viewModel.passwordValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> binding.passwordTextInput.error = it.data?.run { getString(this) }
                else -> binding.passwordTextInput.isErrorEnabled = false
            }
        })

        viewModel.loggingIn.observe(this, Observer {
            binding.pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.messageStringId.observe(this, Observer {
            if (it.status == Status.ERROR) {
                Toast.makeText(context, it.data?.run { getString(this) }, Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun getLayoutResourceId(): Int = R.layout.signup_fragment

}
