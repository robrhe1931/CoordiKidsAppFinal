package com.example.coordikidsapp.ui.auth.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coordikidsapp.R
import com.example.coordikidsapp.commons.Constants
import com.example.coordikidsapp.databinding.FragmentLoginBinding
import com.example.coordikidsapp.ui.auth.forgot_pass.ForgotPassFragment
import com.example.coordikidsapp.ui.auth.signup.signup_details.SignupDetailsFragmentDirections
import com.example.coordikidsapp.ui.dashboard.MainActivity
import com.example.coordikidsapp.utils.safeNavigate
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.facebook.FacebookException

import com.facebook.login.LoginResult

import com.facebook.FacebookCallback

import com.facebook.login.LoginManager
import com.google.firebase.auth.FacebookAuthProvider
import java.util.*


class LoginFragment : Fragment() {

    private val args by navArgs<LoginFragmentArgs>()
    private val isLoginParent by lazy { args.isLoginParent }

    private var rememberStatus: Boolean = false
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: LoginViewModel
    private lateinit var googleSignInClient: GoogleSignInClient
    private var _binding: FragmentLoginBinding? = null
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    private lateinit var factory: LoginViewModelFactory

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isLoginParent) {
            binding.btnBack.visibility = View.GONE
        } else {
            binding.btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
            binding.btnGuestLogin.visibility = View.GONE
        }
        sharedPreferences =
            requireActivity().getSharedPreferences(Constants.AUTH_PREFS, Context.MODE_PRIVATE)

        factory = LoginViewModelFactory(sharedPreferences)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_id))
            .requestEmail()
            .build()


        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        googleSignInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    val data: Intent? = result.data
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    try {
                        // Google Sign In was successful, authenticate with Firebase
                        val account = task.getResult(ApiException::class.java)!!
                        Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                        firebaseAuthGoogle(account.idToken!!)
                    } catch (e: ApiException) {
                        // Google Sign In failed, update UI appropriately
                        Log.w(TAG, "Google sign in failed", e)
                    }
                }
            }

        setOnClickListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.loginStatusLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it) {
                    //Starts UserDetailsActivity class
                    sharedPreferences.edit().putBoolean("LOGIN_STATUS", true).apply()
                    sharedPreferences.edit().putBoolean("REMEMBER_STATUS", rememberStatus).apply()
                    val intent = Intent(context, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    //Shows toast of login error
                    sharedPreferences.edit().putBoolean("LOGIN_STATUS", false).apply()
                    Toast.makeText(context, "Login error: Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun firebaseAuthGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        viewModel.firebaseAuthWithGoogle(credential)
    }

    private fun setOnClickListeners() {

        binding.btnRegularLogin.setOnClickListener {
            regularLogin()
        }
        binding.btnGoogleLogin.setOnClickListener {
            googleLogin()
        }
        binding.btnGuestLogin.setOnClickListener {
            sharedPreferences.edit().putBoolean("LOGIN_STATUS", false).apply()
            sharedPreferences.edit().putBoolean("REMEMBER_STATUS", false).apply()
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.checkboxRemember.setOnCheckedChangeListener { compoundButton, status ->
            rememberStatus = status
        }

        binding.btnFbLogin.setOnClickListener {
            val intent = Intent(context, FacebookAuthActivity::class.java)
            startActivity(intent)
        }

        binding.btnForgotPass.setOnClickListener {
            findNavController().safeNavigate(
                LoginFragmentDirections.actionLoginFragment2ToForgotPassFragment()
            )
        }
    }


    private fun validateEmailPassword(email: String, password: String): Boolean {
        // Check for a valid email address.
        val isEmailValid: Boolean = if (email.isEmpty()) {
            false
        } else Patterns.EMAIL_ADDRESS.matcher(email).matches()

        // Check for a valid password.
        val isPasswordValid: Boolean = if (password.isEmpty()) {
            false
        } else password.length >= 6

        return isEmailValid and isPasswordValid
    }

    private fun regularLogin() {
        hideKeyboard()
        var email = binding.edtLoginUsername.text.toString()
        val password = binding.edtLoginPassword.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email += "@${getString(R.string.default_domain_name)}"
        }

        if (validateEmailPassword(email, password)) {
//            progressLogin.show()
            viewModel.regularLogin(email, password)

        } else {
//            progressLogin.hide()
        }
    }

    private fun googleLogin() {
        hideKeyboard()
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun hideKeyboard() {
        try {
            val inputMethodManager =
                context?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val TAG = "LOGIN_FRAGMENT"
        fun newInstance() = LoginFragment()
    }
}