package com.example.coordikidsapp.ui.auth.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.coordikidsapp.commons.Constants
import com.example.coordikidsapp.databinding.ActivityFacebookAuthBinding
import com.example.coordikidsapp.ui.dashboard.MainActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import java.util.*

// Adding FacebookAuthActivity to enable Fackbook Login
class FacebookAuthActivity : AppCompatActivity() {

    private var rememberStatus: Boolean = false
    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityFacebookAuthBinding
    private lateinit var callbackManager: CallbackManager

    private lateinit var factory: LoginViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFacebookAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences =
            getSharedPreferences(Constants.AUTH_PREFS, Context.MODE_PRIVATE)

        factory = LoginViewModelFactory(sharedPreferences)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance()
            .logInWithReadPermissions(this, Arrays.asList("email"))
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    finish()
                }

                override fun onError(error: FacebookException) {
                    finish()
                }

                override fun onSuccess(result: LoginResult) {
                    firebaseAuthFacebook(result.accessToken)
                }

            })

        setObservers()
    }

    private fun setObservers() {
        viewModel.loginStatusLiveData.observe(this) {
            if (it != null) {
                if (it) {
                    //Starts UserDetailsActivity class
                    sharedPreferences.edit().putBoolean("LOGIN_STATUS", true).apply()
                    sharedPreferences.edit().putBoolean("REMEMBER_STATUS", rememberStatus).apply()
                    val intent = Intent(this@FacebookAuthActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    //Shows toast of login error
                    sharedPreferences.edit().putBoolean("LOGIN_STATUS", false).apply()
                    Toast.makeText(
                        this@FacebookAuthActivity,
                        "Login error: Something went wrong",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                finish()
            }
        }
    }

    private fun firebaseAuthFacebook(accessToken: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        viewModel.firebaseAuthWithFb(credential)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}