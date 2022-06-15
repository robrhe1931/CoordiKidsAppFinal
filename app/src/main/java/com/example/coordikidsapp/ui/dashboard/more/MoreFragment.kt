package com.example.coordikidsapp.ui.dashboard.more

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.coordikidsapp.R
import com.example.coordikidsapp.commons.Constants
import com.example.coordikidsapp.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {

    companion object {
        fun newInstance() = MoreFragment()
    }

    private lateinit var sharedPreferences: SharedPreferences
    private var _binding: FragmentMoreBinding? = null
    private val viewModel: MoreViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        val root = binding.root

        sharedPreferences =
            requireActivity().getSharedPreferences(Constants.AUTH_PREFS, Context.MODE_PRIVATE)

        if(!sharedPreferences.getBoolean("LOGIN_STATUS", false)){
            binding.myAccountOrLogin.text = "Login"
            binding.message.visibility = View.GONE
            binding.myAccountOrLogin.setOnClickListener {
                Navigation.findNavController(root)
                    .navigate(R.id.action_navigation_more_to_loginFragment)
            }
        }else{
            binding.myAccountOrLogin.setOnClickListener {
                Navigation.findNavController(root)
                    .navigate(R.id.action_navigation_more_to_myAccountFragment)
            }
        }
        return root
    }


}