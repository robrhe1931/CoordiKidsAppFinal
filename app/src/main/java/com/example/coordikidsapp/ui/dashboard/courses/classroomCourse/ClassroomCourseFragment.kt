package com.example.coordikidsapp.ui.dashboard.courses.classroomCourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coordikidsapp.R
import com.example.coordikidsapp.databinding.FragmentClassroomCourseBinding
import com.example.coordikidsapp.utils.safeNavigate

class ClassroomCourseFragment : Fragment() {

    private var isLevel1ContentVisible = false

    companion object {
        fun newInstance() = ClassroomCourseFragment()
    }

    private var _binding: FragmentClassroomCourseBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ClassroomCourseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClassroomCourseBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.classroomCourseMovementBreaks.setOnClickListener {
            if (!isLevel1ContentVisible) {
                binding.classroomCourseMovementBreaksContent.visibility = View.VISIBLE
                binding.classroomCourseMovementBreaks.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                isLevel1ContentVisible = true
            } else {
                binding.classroomCourseMovementBreaksContent.visibility = View.GONE
                binding.classroomCourseMovementBreaks.setCompoundDrawablesWithIntrinsicBounds(
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
        binding.btnClassroomStartFreeTrial.setOnClickListener {
            findNavController().safeNavigate(
                ClassroomCourseFragmentDirections.actionClassroomCourseFragmentToSignupOrderFragment(
                    "Classroom Course",
                    "100"
                )
            )
        }
    }
}