package com.example.coordikidsapp.ui.dashboard.courses.homeCourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coordikidsapp.R
import com.example.coordikidsapp.databinding.FragmentHomeCourseBinding
import com.example.coordikidsapp.utils.safeNavigate

class HomeCourseFragment : Fragment() {

    private var isLevel1ContentVisible = false
    private var isLevel2ContentVisible = false
    private var isLevel3ContentVisible = false
    private var isLevel4ContentVisible = false
    private var isLevel5ContentVisible = false
    private var isLevel6ContentVisible = false
    private var isLevel7ContentVisible = false
    private var isLevel8ContentVisible = false

    private var _binding: FragmentHomeCourseBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeCourseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeCourseBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.homeCourseLevel1.setOnClickListener {
            if (!isLevel1ContentVisible) {
                binding.homeCourseLevel1Content.visibility = View.VISIBLE
                binding.homeCourseLevel1.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                isLevel1ContentVisible = true
            } else {
                binding.homeCourseLevel1Content.visibility = View.GONE
                binding.homeCourseLevel1.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_down,
                    0
                )
                isLevel1ContentVisible = false
            }
        }

        binding.homeCourseLevel2.setOnClickListener {
            if (!isLevel2ContentVisible) {
                binding.homeCourseLevel2Content.visibility = View.VISIBLE
                binding.homeCourseLevel2.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                isLevel2ContentVisible = true
            } else {
                binding.homeCourseLevel2Content.visibility = View.GONE
                binding.homeCourseLevel2.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_down,
                    0
                )
                isLevel2ContentVisible = false
            }
        }

        binding.homeCourseLevel3.setOnClickListener {
            if (!isLevel3ContentVisible) {
                binding.homeCourseLevel3Content.visibility = View.VISIBLE
                binding.homeCourseLevel3.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                isLevel3ContentVisible = true
            } else {
                binding.homeCourseLevel3Content.visibility = View.GONE
                binding.homeCourseLevel3.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_down,
                    0
                )
                isLevel3ContentVisible = false
            }
        }

        binding.homeCourseLevel4.setOnClickListener {
            if (!isLevel4ContentVisible) {
                binding.homeCourseLevel4Content.visibility = View.VISIBLE
                binding.homeCourseLevel4.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                isLevel4ContentVisible = true
            } else {
                binding.homeCourseLevel4Content.visibility = View.GONE
                binding.homeCourseLevel4.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_down,
                    0
                )
                isLevel4ContentVisible = false
            }
        }

        binding.homeCourseLevel5.setOnClickListener {
            if (!isLevel5ContentVisible) {
                binding.homeCourseLevel5Content.visibility = View.VISIBLE
                binding.homeCourseLevel5.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                isLevel5ContentVisible = true
            } else {
                binding.homeCourseLevel5Content.visibility = View.GONE
                binding.homeCourseLevel5.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_down,
                    0
                )
                isLevel5ContentVisible = false
            }
        }

        binding.homeCourseLevel6.setOnClickListener {
            if (!isLevel6ContentVisible) {
                binding.homeCourseLevel6Content.visibility = View.VISIBLE
                binding.homeCourseLevel6.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                isLevel6ContentVisible = true
            } else {
                binding.homeCourseLevel6Content.visibility = View.GONE
                binding.homeCourseLevel6.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_down,
                    0
                )
                isLevel6ContentVisible = false
            }
        }

        binding.homeCourseLevel7.setOnClickListener {
            if (!isLevel7ContentVisible) {
                binding.homeCourseLevel7Content.visibility = View.VISIBLE
                binding.homeCourseLevel7.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                isLevel7ContentVisible = true
            } else {
                binding.homeCourseLevel7Content.visibility = View.GONE
                binding.homeCourseLevel7.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_down,
                    0
                )
                isLevel7ContentVisible = false
            }
        }

        binding.homeCourseLevel8.setOnClickListener {
            if (!isLevel8ContentVisible) {
                binding.homeCourseLevel8Content.visibility = View.VISIBLE
                binding.homeCourseLevel8.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                isLevel8ContentVisible = true
            } else {
                binding.homeCourseLevel8Content.visibility = View.GONE
                binding.homeCourseLevel8.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_down,
                    0
                )
                isLevel8ContentVisible = false
            }
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnHomeStartFreeTrial.setOnClickListener {
            findNavController().safeNavigate(
                HomeCourseFragmentDirections.actionHomeCourseFragmentToSignupOrderFragment(
                    "Home Course",
                    "100"
                )
            )
        }
    }

}