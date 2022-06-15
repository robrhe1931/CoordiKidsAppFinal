package com.example.coordikidsapp.ui.dashboard.courses.homeschoolCourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coordikidsapp.R
import com.example.coordikidsapp.databinding.FragmentHomeschoolCourseBinding
import com.example.coordikidsapp.utils.safeNavigate

class HomeschoolCourseFragment : Fragment() {

    private var isLevel1ContentVisible = false

    private var _binding: FragmentHomeschoolCourseBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeschoolCourseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeschoolCourseBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.homeschoolCourseMovementBreaks.setOnClickListener {
            if (!isLevel1ContentVisible) {
                binding.homeschoolCourseMovementBreaksContent.visibility = View.VISIBLE
                binding.homeschoolCourseMovementBreaks.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                isLevel1ContentVisible = true
            } else {
                binding.homeschoolCourseMovementBreaksContent.visibility = View.GONE
                binding.homeschoolCourseMovementBreaks.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_down,
                    0
                )
                isLevel1ContentVisible = false
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnHomeschoolStartFreeTrial.setOnClickListener {
            findNavController().safeNavigate(
                HomeschoolCourseFragmentDirections.actionHomeschoolCourseFragmentToSignupOrderFragment(
                    "Homeschool Course",
                    "100"
                )
            )
        }
    }
}