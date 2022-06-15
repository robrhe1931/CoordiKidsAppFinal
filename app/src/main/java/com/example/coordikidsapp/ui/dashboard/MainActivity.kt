package com.example.coordikidsapp.ui.dashboard

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.coordikidsapp.R
import com.example.coordikidsapp.commons.Constants
import com.example.coordikidsapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences =
            getSharedPreferences(Constants.AUTH_PREFS, Context.MODE_PRIVATE)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_courses,
                R.id.navigation_notifications,
                R.id.navigation_more
            )
        )

        val destinationChangedListener =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.loginFragment -> {
                        navView.visibility = View.GONE
                    }
                    R.id.signupOrderFragment -> {
                        navView.visibility = View.GONE
                    }
                    R.id.signupDetailsFragment -> {
                        navView.visibility = View.GONE
                    }
                    R.id.signupBillingFragment -> {
                        navView.visibility = View.GONE
                    }
                    else -> {
                        navView.visibility = View.VISIBLE
                    }
                }
            }

//        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener(destinationChangedListener)
        navView.setupWithNavController(navController)
    }
}