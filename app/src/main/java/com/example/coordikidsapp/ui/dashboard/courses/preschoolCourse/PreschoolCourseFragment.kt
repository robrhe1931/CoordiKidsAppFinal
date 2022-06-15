package com.example.coordikidsapp.ui.dashboard.courses.preschoolCourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coordikidsapp.R
import com.example.coordikidsapp.databinding.FragmentPreschoolCourseBinding
import com.example.coordikidsapp.utils.safeNavigate

class PreschoolCourseFragment : Fragment() {

    private var isLevel1ContentVisible = false
    private var isLevel2ContentVisible = false
    private var isLevel3ContentVisible = false
    private var isLevel4ContentVisible = false

    private var _binding: FragmentPreschoolCourseBinding? = null
    private val binding get() = _binding!!

    private  val viewModel: PreschoolCourseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPreschoolCourseBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.preschoolCourseLevel1.setOnClickListener {
            if (!isLevel1ContentVisible) {
                binding.preschoolCourseLevel1Content.visibility = View.VISIBLE
                binding.preschoolCourseLevel1.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                isLevel1ContentVisible = true
            } else {
                binding.preschoolCourseLevel1Content.visibility = View.GONE
                binding.preschoolCourseLevel1.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_down,
                    0
                )
                isLevel1ContentVisible = false
            }
        }

        binding.preschoolCourseLevel2.setOnClickListener {
            if (!isLevel2ContentVisible) {
                binding.preschoolCourseLevel2Content.visibility = View.VISIBLE
                binding.preschoolCourseLevel2.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                isLevel2ContentVisible = true
            } else {
                binding.preschoolCourseLevel2Content.visibility = View.GONE
                binding.preschoolCourseLevel2.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_down,
                    0
                )
                isLevel2ContentVisible = false
            }
        }

        binding.preschoolCourseLevel3.setOnClickListener {
            if (!isLevel3ContentVisible) {
                binding.preschoolCourseLevel3Content.visibility = View.VISIBLE
                binding.preschoolCourseLevel3.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                isLevel3ContentVisible = true
            } else {
                binding.preschoolCourseLevel3Content.visibility = View.GONE
                binding.preschoolCourseLevel3.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_down,
                    0
                )
                isLevel3ContentVisible = false
            }
        }

        binding.preschoolCourseLevel4.setOnClickListener {
            if (!isLevel4ContentVisible) {
                binding.preschoolCourseLevel4Content.visibility = View.VISIBLE
                binding.preschoolCourseLevel4.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                isLevel4ContentVisible = true
            } else {
                binding.preschoolCourseLevel4Content.visibility = View.GONE
                binding.preschoolCourseLevel4.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_down,
                    0
                )
                isLevel4ContentVisible = false
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPreschoolStartFreeTrial.setOnClickListener {
                findNavController().safeNavigate(
                    PreschoolCourseFragmentDirections.actionPreschoolCourseFragmentToSignupOrderFragment(
                        "Preschool Course",
                        "100"
                    )
                )
        }
    }
}