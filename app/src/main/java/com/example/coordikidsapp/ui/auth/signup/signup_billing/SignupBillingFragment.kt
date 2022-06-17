package com.example.coordikidsapp.ui.auth.signup.signup_billing

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coordikidsapp.commons.Constants
import com.example.coordikidsapp.databinding.FragmentSignupBillingBinding
import com.example.coordikidsapp.domain.models.BillingDetails
import com.example.coordikidsapp.domain.models.CourseOrder
import com.google.android.material.snackbar.Snackbar
import java.util.*

class SignupBillingFragment : Fragment() {

    private val args by navArgs<SignupBillingFragmentArgs>()
    private val orderDetails by lazy { args.orderDetails }
    private val userDetails by lazy { args.userDetails }

    private lateinit var authSharedPreferences: SharedPreferences
    private lateinit var profileSharedPreferences: SharedPreferences
    private var _binding: FragmentSignupBillingBinding? = null

    private val binding get() = _binding!!

    private lateinit var factory: SignupBillingViewModelFactory
    private lateinit var viewModel: SignupBillingViewModel

    private var loginStatus = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBillingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authSharedPreferences =
            requireActivity().getSharedPreferences(Constants.AUTH_PREFS, Context.MODE_PRIVATE)

        loginStatus = authSharedPreferences.getBoolean("LOGIN_STATUS", false)

        if(loginStatus){
            binding.tvStep.text = "Step 2/2"
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        profileSharedPreferences = requireActivity().getSharedPreferences(
            Constants.USER_PROFILE_PREFS,
            Context.MODE_PRIVATE
        )

        factory = SignupBillingViewModelFactory(profileSharedPreferences)
        viewModel = ViewModelProvider(this, factory)[SignupBillingViewModel::class.java]

        binding.btnPayment.setOnClickListener {
            if (validateDetails()) {
                val billingRegion = binding.edtBillingRegion.text.toString()
                val houseNumber = binding.edtPaymentHouseNo.text.toString()
                val streetAddress = binding.edtPaymentStreetAddress.text.toString()
                val suburb = binding.edtPaymentSuburb.text.toString()
                val state = binding.edtPaymentState.text.toString()
                val postcode = binding.edtPaymentPostcode.text.toString()

                val agreeTerms = binding.checkboxTermsConditions.isChecked
                val subscribeStatus = binding.checkboxSubscribe.isChecked

                val billingDetails = BillingDetails(
                    billingRegion,
                    houseNumber,
                    streetAddress,
                    suburb,
                    state,
                    postcode,
                    agreeTerms,
                    subscribeStatus
                )
                val orderId = UUID.randomUUID().toString()
                val courseOrder =
                    CourseOrder(orderId, orderDetails, userDetails, billingDetails)
                if (!loginStatus) {
                    viewModel.createOrderRegister(courseOrder)
                } else {
                    viewModel.createOrder(courseOrder)
                }
            }
        }

        viewModel.orderStatusLiveData.observe(viewLifecycleOwner) {
            if (it && loginStatus) {
                Toast.makeText(
                    context,
                    "Order is successful and user is already logged in",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (it && !loginStatus) {
                Toast.makeText(
                    context,
                    "Order is successful and user is not logged in",
                    Toast.LENGTH_SHORT
                ).show()
                authSharedPreferences.edit().putBoolean("LOGIN_STATUS", true).commit()
            } else if (!it && loginStatus) {
                Toast.makeText(
                    context,
                    "Order is not successful and user is already logged in",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Order is not successful and user is not logged in",
                    Toast.LENGTH_SHORT
                ).show()
                authSharedPreferences.edit().putBoolean("LOGIN_STATUS", false).commit()
            }
        }
    }


    private fun validateDetails(): Boolean {
        val billingRegion = binding.edtBillingRegion.text.toString()
        val houseNumber = binding.edtPaymentHouseNo.text.toString()
        val streetAddress = binding.edtPaymentStreetAddress.text.toString()
        val suburb = binding.edtPaymentSuburb.text.toString()
        val state = binding.edtPaymentState.text.toString()
        val postcode = binding.edtPaymentPostcode.text.toString()

        if (billingRegion.isEmpty()) {
            showSnackBar("Please enter your country/region")
//            Toast.makeText(context, "Please enter your country/region", Toast.LENGTH_SHORT)
//                .show()
            return false
        } else if (houseNumber.isEmpty()
        ) {
            showSnackBar("Please enter your house number")
//            Toast.makeText(context, "Please enter your house number", Toast.LENGTH_SHORT)
//                .show()
            return false
        } else if (streetAddress.isEmpty()) {
            showSnackBar("Please enter your street address")
//            Toast.makeText(
//                context,
//                "Please enter your street address",
//                Toast.LENGTH_SHORT
//            ).show()
            return false
        } else if (suburb.isEmpty()) {
            showSnackBar("Please enter your suburb area")
//            Toast.makeText(
//                context,
//                "Please enter your suburb area",
//                Toast.LENGTH_SHORT
//            ).show()
            return false
        } else if (state.isEmpty()) {
            showSnackBar("Please enter your state")
//            Toast.makeText(
//                context,
//                "Please enter your state",
//                Toast.LENGTH_SHORT
//            ).show()
            return false
        } else if (postcode.isEmpty()) {
            showSnackBar("Please enter your postcode number")
//            Toast.makeText(
//                context,
//                "Please enter your postcode number",
//                Toast.LENGTH_SHORT
//            ).show()
            return false
        } else if (!binding.checkboxTermsConditions.isChecked) {
            showSnackBar("Please agree to all terms and conditions")
//            Toast.makeText(
//                context,
//                "Please agree to all terms and conditions",
//                Toast.LENGTH_SHORT
//            ).show()
            return false
        } else {
            return true
        }
    }

    private fun showSnackBar(errorText: String) {
        val snackbar = Snackbar
            .make(binding.billingContainer, errorText, Snackbar.LENGTH_LONG)
        snackbar.anchorView?.setBackgroundColor(Color.RED)
        snackbar.setTextColor(Color.WHITE)
        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}