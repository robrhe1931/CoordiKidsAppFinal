package com.example.coordikidsapp.ui.dashboard.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.coordikidsapp.R
import com.example.coordikidsapp.databinding.FragmentCoursesBinding

class CoursesFragment : Fragment() {

    private var homeCourseVisibilityStatus: Boolean = false
    private var preschoolCourseVisibilityStatus: Boolean = false
    private var classroomCourseVisibilityStatus: Boolean = false
    private var homeschoolCourseVisibilityStatus: Boolean = false

    private var _binding: FragmentCoursesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(CoursesViewModel::class.java)

        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnHomeCourse.setOnClickListener {
            Navigation.findNavController(root)
                .navigate(R.id.action_navigation_courses_to_homeCourseFragment)
            resetVisibilityStatus()
        }

        binding.btnPreschoolCourse.setOnClickListener {
            Navigation.findNavController(root)
                .navigate(R.id.action_navigation_courses_to_preschoolCourseFragment)
            resetVisibilityStatus()
        }

        binding.btnClassroomCourse.setOnClickListener {
            Navigation.findNavController(root)
                .navigate(R.id.action_navigation_courses_to_classroomCourseFragment)
            resetVisibilityStatus()
        }

        binding.btnHomeschoolCourse.setOnClickListener {
            Navigation.findNavController(root)
                .navigate(R.id.action_navigation_courses_to_homeschoolCourseFragment)
            resetVisibilityStatus()
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeCourseTextView: TextView = binding.homeCourse
        homeCourseTextView.setOnClickListener {
            if (!homeCourseVisibilityStatus) {
                binding.layoutHomeCourse.visibility = View.VISIBLE
                binding.homeCourseDivider.visibility = View.VISIBLE
                homeCourseTextView.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                homeCourseVisibilityStatus = true
            } else {
                binding.layoutHomeCourse.visibility = View.GONE
                binding.homeCourseDivider.visibility = View.GONE
                homeCourseTextView.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_down,
                    0
                )
                homeCourseVisibilityStatus = false
            }
        }

        val preschoolCourseTextView: TextView = binding.preschoolCourse
        preschoolCourseTextView.setOnClickListener {
            if (!preschoolCourseVisibilityStatus) {
                binding.layoutPreschoolCourse.visibility = View.VISIBLE
                binding.preschoolCourseDivider.visibility = View.VISIBLE
                preschoolCourseTextView.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                preschoolCourseVisibilityStatus = true
            } else {
                binding.layoutPreschoolCourse.visibility = View.GONE
                binding.preschoolCourseDivider.visibility = View.GONE
                preschoolCourseTextView.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_down,
                    0
                )
                preschoolCourseVisibilityStatus = false
            }
        }

        val classroomCourseTextView: TextView = binding.classroomCourse
        classroomCourseTextView.setOnClickListener {
            if (!classroomCourseVisibilityStatus) {
                binding.layoutClassroomCourse.visibility = View.VISIBLE
                binding.classroomCourseDivider.visibility = View.VISIBLE
                classroomCourseTextView.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                classroomCourseVisibilityStatus = true
            } else {
                binding.layoutClassroomCourse.visibility = View.GONE
                binding.classroomCourseDivider.visibility = View.GONE
                classroomCourseTextView.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_down,
                    0
                )
                classroomCourseVisibilityStatus = false
            }
        }

        val homeschoolCourseTextView: TextView = binding.homeschoolCourse
        homeschoolCourseTextView.setOnClickListener {
            if (!homeschoolCourseVisibilityStatus) {
                binding.layoutHomeschoolCourse.visibility = View.VISIBLE
                binding.homeschoolCourseDivider.visibility = View.VISIBLE
                homeschoolCourseTextView.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_up,
                    0
                )
                homeschoolCourseVisibilityStatus = true
            } else {
                binding.layoutHomeschoolCourse.visibility = View.GONE
                binding.homeschoolCourseDivider.visibility = View.GONE
                homeschoolCourseTextView.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_drop_down,
                    0
                )
                homeschoolCourseVisibilityStatus = false
            }
        }
    }

    private fun resetVisibilityStatus() {
        homeCourseVisibilityStatus = false
        preschoolCourseVisibilityStatus = false
        classroomCourseVisibilityStatus = false
        homeschoolCourseVisibilityStatus = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}