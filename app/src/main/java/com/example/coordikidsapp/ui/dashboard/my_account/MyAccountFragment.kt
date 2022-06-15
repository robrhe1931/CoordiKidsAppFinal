package com.example.coordikidsapp.ui.dashboard.my_account

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.coordikidsapp.commons.Constants
import com.example.coordikidsapp.databinding.FragmentMyAccountBinding
import com.google.firebase.auth.FirebaseAuth

class MyAccountFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private var _binding: FragmentMyAccountBinding? = null
    private val viewModel: MyAccountViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        sharedPreferences =
            requireActivity().getSharedPreferences(Constants.AUTH_PREFS, Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logout.setOnClickListener {
            sharedPreferences.edit().putBoolean("LOGIN_STATUS", false).apply()
            FirebaseAuth.getInstance().signOut()
            activity?.finish()
        }
    }
}