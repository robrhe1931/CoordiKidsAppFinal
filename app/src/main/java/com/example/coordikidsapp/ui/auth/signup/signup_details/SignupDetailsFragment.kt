package com.example.coordikidsapp.ui.auth.signup.signup_details

import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coordikidsapp.R
import com.example.coordikidsapp.commons.CustomDropDownAdapter
import com.example.coordikidsapp.databinding.FragmentSignupDetailsBinding
import com.example.coordikidsapp.domain.models.UserDetails
import com.example.coordikidsapp.utils.safeNavigate
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*


class SignupDetailsFragment : Fragment() {

    private val args by navArgs<SignupDetailsFragmentArgs>()
    private val orderDetails by lazy { args.orderDetails }

    private val viewModel: SignupDetailsViewModel by viewModels()

    private var _binding: FragmentSignupDetailsBinding? = null

    private val binding get() = _binding!!
    private var datePickerStatus = false

    private var date = ""
    private var month = ""
    private var year = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }


        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val currentDateAndTime: String = simpleDateFormat.format(Date())
        date = currentDateAndTime.substring(0, 2)
        month = currentDateAndTime.substring(3, 5)
        year = currentDateAndTime.substring(6, 10)

        val currentYear = Integer.parseInt(year)
        val yearOptions = arrayOfNulls<String>(currentYear - 1950)

        for (i in yearOptions.indices) {
            yearOptions[i] = (1950 + i).toString()
        }


        val dateOptions =
            if (month == "Jan" || month == "March" || month == "May" || month == "July" || month == "August" || month == "Oct" || month == "Dec") {
                resources.getStringArray(R.array.date_options_1)
            } else if (month == "Feb" && Integer.parseInt(year) % 4 == 0) {
                resources.getStringArray(R.array.date_options_3)
            } else if (month == "Feb" && Integer.parseInt(year) % 4 != 0) {
                resources.getStringArray(R.array.date_options_2)
            } else {
                resources.getStringArray(R.array.date_options_4)
            }

        val dateAdapter = CustomDropDownAdapter(requireContext(), dateOptions)
        binding.spinnerDate.adapter = dateAdapter

        val monthOptions = resources.getStringArray(R.array.month_options)

        val monthAdapter = CustomDropDownAdapter(requireContext(), monthOptions)
        binding.spinnerMonth.adapter = monthAdapter

        val yearAdapter = CustomDropDownAdapter(
            requireContext(),
            yearOptions
        )
        binding.spinnerYear.adapter = yearAdapter

        binding.spinnerDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position < dateOptions.size) {
                    date = dateOptions[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        binding.spinnerMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                month = monthOptions[position]
                val options =
                    if (month == "Jan" || month == "March" || month == "May" || month == "July" || month == "August" || month == "Oct" || month == "Dec") {
                        resources.getStringArray(R.array.date_options_1)
                    } else if (month == "Feb" && Integer.parseInt(year) % 4 == 0) {
                        resources.getStringArray(R.array.date_options_3)
                    } else if (month == "Feb" && Integer.parseInt(year) % 4 != 0) {
                        resources.getStringArray(R.array.date_options_2)
                    } else {
                        resources.getStringArray(R.array.date_options_4)
                    }

                val adapter = CustomDropDownAdapter(requireContext(), options)
                binding.spinnerDate.adapter = adapter
                binding.spinnerDate.setSelection(options.indexOf(date))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }


        binding.spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                year = yearOptions[position].toString()
                if (month == "Feb") {
                    val options = if (Integer.parseInt(year) % 4 == 0) {
                        resources.getStringArray(R.array.date_options_3)
                    } else {
                        resources.getStringArray(R.array.date_options_2)
                    }

                    val adapter = CustomDropDownAdapter(requireContext(), options)
                    binding.spinnerDate.adapter = adapter
                    binding.spinnerDate.setSelection(options.indexOf(date))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        binding.btnDetailsNext.setOnClickListener {
            if (validateDetails()) {
                val userDetails = UserDetails(
                    caregiverFName = binding.edtSignupFirstName.text.toString(),
                    caregiverLName = binding.edtSignupLastName.text.toString(),
                    phoneNumber = binding.edtSignupPhone.text.toString(),
                    email = binding.edtSignupEmail.text.toString(),
                    password = binding.edtSignupPassword.text.toString(),
                    childFName = binding.edtSignupChildFname.text.toString(),
                    childLName = binding.edtSignupChildLname.text.toString(),
                    childDOB = "$date/$month//$year"
                )
                findNavController().safeNavigate(
                    SignupDetailsFragmentDirections.actionSignupDetailsFragmentToSignupBillingFragment(
                        orderDetails,
                        userDetails
                    )
                )
            }
        }

    }

    private fun validateDetails(): Boolean {
        val caregiverFName = binding.edtSignupFirstName.text.toString()
        val caregiverLName = binding.edtSignupLastName.text.toString()
        val phoneNumber = binding.edtSignupPhone.text.toString()
        val email = binding.edtSignupEmail.text.toString()
        val password = binding.edtSignupPassword.text.toString()
        val childFName = binding.edtSignupChildFname.text.toString()
        val childLName = binding.edtSignupChildLname.text.toString()

        if (childFName.isEmpty()) {
            showSnackBar("Please enter child's first name")
//            Toast.makeText(
//                context,
//                "Please enter child's first name",
//                Toast.LENGTH_SHORT
//            ).show()
            return false
        } else if (childLName.isEmpty()) {
            showSnackBar("Please enter child's last name")
//            Toast.makeText(
//                context,
//                "Please enter child's last name",
//                Toast.LENGTH_SHORT
//            ).show()
            return false
        } else if (date == "" && month == "" && year == "") {
            showSnackBar("Please choose child's date of birth")
//            Toast.makeText(
//                context,
//                "Please choose child's date of birth",
//                Toast.LENGTH_SHORT
//            ).show()
            return false
        } else if (caregiverFName.isEmpty()) {
            showSnackBar("Please enter caregiver's first name")
//            Toast.makeText(context, "Please enter caregiver's first name", Toast.LENGTH_SHORT)
//                .show()
            return false
        } else if (caregiverLName.isEmpty()
        ) {
            showSnackBar("Please enter caregiver's last name")
//            Toast.makeText(context, "Please enter caregiver's last name", Toast.LENGTH_SHORT)
//                .show()
            return false
        } else if (phoneNumber.isEmpty() or !Patterns.PHONE.matcher(phoneNumber).matches()) {
            showSnackBar("Please enter phone number correctly")
//            Toast.makeText(
//                context,
//                "Please enter phone number correctly",
//                Toast.LENGTH_SHORT
//            ).show()
            return false
        } else if (email.isEmpty() or !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showSnackBar("Please enter email correctly")
//            Toast.makeText(
//                context,
//                "Please enter email correctly",
//                Toast.LENGTH_SHORT
//            ).show()
            return false
        } else if (password.isEmpty() or (password.length < 6)) {
            showSnackBar("Please enter password correctly")
//            Toast.makeText(
//                context,
//                "Please enter password correctly",
//                Toast.LENGTH_SHORT
//            ).show()
            return false
        } else {
            return true
        }
    }

    private fun showSnackBar(errorText: String) {
        val snackbar = Snackbar
            .make(binding.container, errorText, Snackbar.LENGTH_LONG)
        snackbar.anchorView?.setBackgroundColor(Color.RED)
        snackbar.setTextColor(Color.WHITE)
        snackbar.show()
    }
}