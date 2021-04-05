package com.third.eye.thirdeyefortune.ui.main.viewModels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.navigation.findNavController
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.third.eye.thirdeyefortune.R
import com.third.eye.thirdeyefortune.log.Logger
import com.third.eye.thirdeyefortune.network.NetworkHelper
import com.third.eye.thirdeyefortune.rx.SchedulerProvider
import com.third.eye.thirdeyefortune.ui.base.BaseViewModel
import com.third.eye.thirdeyefortune.ui.main.fragments.LoginFragmentDirections
import com.third.eye.thirdeyefortune.utils.common.*
import io.reactivex.disposables.CompositeDisposable
import java.util.*

open class LoginFragmentViewModel(
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    schedulerProvider: SchedulerProvider,
    private val mAuth: FirebaseAuth,
    private val loginManager: LoginManager,
    private val callbackManager: CallbackManager
) : BaseViewModel(
    compositeDisposable = compositeDisposable,
    networkHelper = networkHelper,
    schedulerProvider = schedulerProvider
) {

    protected val validationsList: MutableLiveData<List<Validation>> = MutableLiveData()
    val launchMain: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()

    val emailField: MutableLiveData<String> = MutableLiveData()
    val passwordField: MutableLiveData<String> = MutableLiveData()
    val usernameField: MutableLiveData<String> = MutableLiveData()
    val loggingIn: MutableLiveData<Boolean> = MutableLiveData()
    val requestFacebookLogin: MutableLiveData<Boolean> = MutableLiveData()

    val emailValidation: LiveData<Resource<Int>> = filterValidation(Validation.Field.EMAIL)
    val passwordValidation: LiveData<Resource<Int>> = filterValidation(Validation.Field.PASSWORD)
    val usernameValidation: LiveData<Resource<Int>> = filterValidation(Validation.Field.USERNAME)

    private fun filterValidation(field: Validation.Field) =
        Transformations.map(validationsList) {
            it.find { validation -> validation.field == field }
                ?.run { return@run this.resource }
                ?: Resource.unknown()
        }

    override fun onCreate() {
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Logger.d("LoginFragmentViewModel", "facebook:onCancel")
            }

            override fun onError(exception: FacebookException) {
                Logger.d("LoginFragmentViewModel", "facebook:onError", exception)
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Logger.d("LoginFragmentViewModel", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Logger.d("LoginFragmentViewModel", "signInWithCredential:success")
                    loggingIn.postValue(false)
                    launchMain.postValue(Event(emptyMap()))
                } else {
                    // If sign in fails, display a message to the user.
                    Logger.w(
                        "LoginFragmentViewModel",
                        "signInWithCredential:failure",
                        task.exception?.message.toString()
                    )
                    messageStringId.postValue(Resource.error(R.string.tef_auth_failed))
                    loggingIn.postValue(false)
                }
            }
    }

    fun onEmailChange(email: String) = emailField.postValue(email)

    fun onPasswordChange(email: String) = passwordField.postValue(email)

    fun onUsernameChange(username: String) = usernameField.postValue(username)

    fun onLogin() {
        val email = emailField.value
        val password = passwordField.value

        val validations = Validator.validateLoginFields(email, password)
        validationsList.postValue(validations)

        if (validations.isNotEmpty() && email != null && password != null) {
            val successValidation = validations.filter { it.resource.status == Status.SUCCESS }
            if (successValidation.size == validations.size && checkInternetConnectionWithMessage()) {
                loggingIn.postValue(true)
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            signInSuccess()
                        } else {
                            // If sign in fails, display a message to the user.
                            signInFailed(task)
                        }
                    }
            }
        }
    }

    private fun signInFailed(task: Task<AuthResult>) {
        Logger.w(
            "LoginFragmentViewModel",
            "signInWithEmail:failure",
            task.exception?.message.toString()
        )
        messageStringId.postValue(Resource.error(R.string.tef_auth_failed))
        loggingIn.postValue(false)
    }

    private fun signInSuccess() {
        // Sign in success, update UI with the signed-in user's information
        Logger.d("LoginFragmentViewModel", "signInWithEmail:success")
        loggingIn.postValue(false)
        launchMain.postValue(Event(emptyMap()))
    }

    fun onCreateAccountClicked(view: View) = view.findNavController()
        .navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())

    fun onFacebookLogin() {
        loggingIn.postValue(true)
        requestFacebookLogin.postValue(true)
    }

    fun onLoginCancelled(view: View) {

    }

}
