package com.third.eye.thirdeyefortune.ui.main.viewModels

import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.third.eye.thirdeyefortune.R
import com.third.eye.thirdeyefortune.log.Logger
import com.third.eye.thirdeyefortune.network.NetworkHelper
import com.third.eye.thirdeyefortune.rx.SchedulerProvider
import com.third.eye.thirdeyefortune.utils.common.Event
import com.third.eye.thirdeyefortune.utils.common.Resource
import com.third.eye.thirdeyefortune.utils.common.Status
import com.third.eye.thirdeyefortune.utils.common.Validator
import io.reactivex.disposables.CompositeDisposable

class SignUpFragmentViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val mAuth: FirebaseAuth,
    private val loginManager: LoginManager,
    private val callbackManager: CallbackManager
) : LoginFragmentViewModel(
    schedulerProvider = schedulerProvider,
    compositeDisposable = compositeDisposable,
    networkHelper = networkHelper,
    mAuth = mAuth,
    loginManager = loginManager,
    callbackManager = callbackManager
) {

    override fun onCreate() {

    }

    fun onSignUp() {
        val email = emailField.value
        val password = passwordField.value
        // val username = usernameField.value

        val validations = Validator.validateLoginFields(email, password)
        validationsList.postValue(validations)

        if (validations.isNotEmpty() && email != null && password != null) {
            val successValidation = validations.filter { it.resource.status == Status.SUCCESS }
            if (successValidation.size == validations.size && checkInternetConnectionWithMessage()) {
                loggingIn.postValue(true)
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Logger.d("SignUpFragmentViewModel", "createUserWithEmail:success")
                            // val user = mAuth.currentUser
                            // updateUI(user)
                            // userRepository.saveCurrentUser(it)
                            loggingIn.postValue(false)
                            launchMain.postValue(Event(emptyMap()))
                        } else {
                            // If sign in fails, display a message to the user.
                            Logger.w(
                                "SignUpFragmentViewModel",
                                "createUserWithEmail:failure",
                                task.exception?.message.toString()
                            )
                            messageStringId.postValue(Resource.error(R.string.tef_auth_failed))
                            loggingIn.postValue(false)
                            // updateUI(null)
                        }
                    }
//                compositeDisposable.addAll(
//                    doUserLogin(email, password)
//                        .subscribeOn(schedulerProvider.io())
//                        .subscribe(
//                            {
//                                // userRepository.saveCurrentUser(it)
//                                loggingIn.postValue(false)
//                                launchMain.postValue(Event(emptyMap()))
//                            },
//                            {
//                                handleNetworkError(it)
//                                loggingIn.postValue(false)
//                            }
//                        )
//                )
            }
        }
    }

}
