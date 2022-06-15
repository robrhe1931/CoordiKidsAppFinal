package com.example.coordikidsapp.ui.auth.signup.signup_order

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coordikidsapp.R
import com.example.coordikidsapp.commons.Constants
import com.example.coordikidsapp.databinding.FragmentSignupOrderBinding
import com.example.coordikidsapp.domain.models.OrderDetails
import com.example.coordikidsapp.domain.models.UserDetails
import com.example.coordikidsapp.commons.CustomDropDownAdapter
import com.example.coordikidsapp.utils.safeNavigate
import com.google.firebase.auth.FirebaseAuth

class SignupOrderFragment : Fragment() {

    private val args by navArgs<SignupOrderFragmentArgs>()
    private val viewModel: SignupOrderViewModel by viewModels()

    private val orderName by lazy { args.orderName }
    private val orderPrice by lazy { args.orderPrice }

    private var _binding: FragmentSignupOrderBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textCourses.text = orderName
        binding.textQty.text = "1"
        binding.textTotal.text = orderPrice

        if(FirebaseAuth.getInstance().currentUser != null){
            binding.tvOrderStep.text  = "Step 1/2"
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnOrderNext.setOnClickListener {
            val orderDetails = OrderDetails(orderName, 1, orderPrice, binding.edtPaymentCoupon.text.toString())
            if (FirebaseAuth.getInstance().currentUser != null) {
                findNavController().safeNavigate(
                    SignupOrderFragmentDirections.actionSignupOrderFragmentToSignupBillingFragment(
                        orderDetails, getUserDetails()
                    )
                )
            } else {
                findNavController().safeNavigate(
                    SignupOrderFragmentDirections.actionSignupOrderFragmentToSignupDetailsFragment(
                        orderDetails
                    )
                )
            }
        }
    }

    private fun getUserDetails(): UserDetails {
        val sharedPreferences = requireActivity().getSharedPreferences(
            Constants.USER_PROFILE_PREFS,
            Context.MODE_PRIVATE
        )
        val caregiverFName = sharedPreferences.getString(Constants.CAREGIVER_FNAME, "") ?: ""
        val caregiverLName = sharedPreferences.getString(Constants.CAREGIVER_LNAME, "") ?: ""
        val phoneNumber = sharedPreferences.getString(Constants.PHONE_NUMBER, "") ?: ""
        val userEmail = sharedPreferences.getString(Constants.USER_EMAIL, "") ?: ""
        val childFName = sharedPreferences.getString(Constants.CHILD_FNAME, "") ?: ""
        val childLName = sharedPreferences.getString(Constants.CHILD_LNAME, "") ?: ""
        val childDOB = sharedPreferences.getString(Constants.CHILD_DOB, "") ?: ""
        return UserDetails(
            caregiverFName,
            caregiverLName,
            phoneNumber,
            userEmail,
            "",
            childFName,
            childLName,
            childDOB
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}