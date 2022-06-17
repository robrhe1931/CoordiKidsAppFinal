package com.example.coordikidsapp.ui.dashboard.more

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
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
//            binding.message.visibility = View.GONE
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contactUs.setOnClickListener {
            val uri = Uri.parse("https://www.coordikids.com/contact-us/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        binding.shareWithFriends.setOnClickListener {
            val uri = Uri.parse("https://www.coordikids.com/contact-us/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        binding.rateUs.setOnClickListener {
            val uri = Uri.parse("https://www.facebook.com/Coordikids/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        binding.rateUs.setOnClickListener {
            val uri = Uri.parse("https://www.google.com/search?q=coordikids&rlz=1C1UEAD_en-GBAU944AU944&sxsrf=ALiCzsagXNnTy_ZG2nS-m62_nghzVFbwfw%3A1655447392250&ei=YB-sYu3TDZTz4-EPob2kkAM&oq=coo&gs_lcp=Cgdnd3Mtd2l6EAMYADIECCMQJzIECAAQQzIECAAQQzIECAAQQzIHCC4QsQMQQzIECAAQQzIRCC4QgAQQsQMQgwEQxwEQrwEyCwguEIAEEMcBEK8BMhEILhCABBCxAxCDARDHARCvATILCC4QgAQQxwEQrwE6BwgjEOoCECc6FAguEIAEELEDEIMBEMcBEKMCENQCOhEILhCABBCxAxCDARDHARCjAjoLCAAQgAQQsQMQgwE6DgguEIAEELEDEMcBEKMCSgQIQRgASgQIRhgAULsFWLIHYOMQaAFwAXgAgAGAAogBywWSAQMyLTOYAQCgAQGwAQrAAQE&sclient=gws-wiz#lrd=0x6b915fca0e00484d:0x6aa4857a3c6f36ad,1,,,")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        binding.blog.setOnClickListener {
            val uri = Uri.parse("https://www.coordikids.com/blog-2/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        binding.faq.setOnClickListener {
            val uri = Uri.parse("https://www.coordikids.com/faqs/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}