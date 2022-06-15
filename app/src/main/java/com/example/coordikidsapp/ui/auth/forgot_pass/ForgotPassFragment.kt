package com.example.coordikidsapp.ui.auth.forgot_pass

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.coordikidsapp.databinding.FragmentForgotPassBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPassFragment : Fragment() {

    private lateinit var viewModel: ForgotPassViewModel

    private var _binding: FragmentForgotPassBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ForgotPassViewModel::class.java]

        binding.btnResetPass.setOnClickListener {
            if (validateDetails()) {
                viewModel.changePassword(binding.edtChangePassEmail.text.toString())
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.changePassStatusLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it) {
                    Toast.makeText(
                        context,
                        " You will receive a link to create a new password via email.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Change password error: Something went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            findNavController().navigateUp()
        }
    }

    private fun validateDetails(): Boolean {
        val email = binding.edtChangePassEmail.text.toString()
        return if (email.isEmpty() or !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Please enter your email to reset password", Toast.LENGTH_SHORT)
                .show()
            false
        } else {
            true
        }
    }

}