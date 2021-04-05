package com.third.eye.thirdeyefortune.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.third.eye.thirdeyefortune.R
import com.third.eye.thirdeyefortune.databinding.LoginFragmentBinding
import com.third.eye.thirdeyefortune.di.components.FragmentComponent
import com.third.eye.thirdeyefortune.ui.base.BaseFragment
import com.third.eye.thirdeyefortune.ui.main.viewModels.LoginFragmentViewModel
import com.third.eye.thirdeyefortune.utils.common.Event
import com.third.eye.thirdeyefortune.utils.common.Status
import javax.inject.Inject

class LoginFragment : BaseFragment<LoginFragmentViewModel, LoginFragmentBinding>() {

    companion object {
        const val TAG = "LoginFragment"
        fun newInstance() = LoginFragment()
    }

    @Inject
    lateinit var loginManager: LoginManager

    @Inject
    lateinit var callbackManager: CallbackManager

    override fun setupView(view: View, savedInstanceState: Bundle?) {

        binding.userEmailText.addTextChangedListener(object : TextWatcher {
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

    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun getLayoutResourceId() = R.layout.login_fragment

    override fun setupObservers() {
        super.setupObservers()

        viewModel.launchMain.observe(this, Observer<Event<Map<String, String>>> {
            it.getIfNotHandled()?.run {
                findNavController().navigate(LoginFragmentDirections.actionGlobalHomeFragment())
            }
        })

        viewModel.emailField.observe(this, Observer {
            if (binding.userEmailText.text.toString() != it) binding.userEmailText.setText(it)
        })

        viewModel.emailValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> binding.userEmail.error = it.data?.run { getString(this) }
                else -> binding.userEmail.isErrorEnabled = false
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

        viewModel.messageStringId.observe(this, Observer {
            if (it.status == Status.ERROR) {
                Toast.makeText(context, it.data?.run { getString(this) }, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.requestFacebookLogin.observe(this, Observer {
            if (it) {
                loginManager.logInWithReadPermissions(this, listOf("email", "public_profile"))
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

}
