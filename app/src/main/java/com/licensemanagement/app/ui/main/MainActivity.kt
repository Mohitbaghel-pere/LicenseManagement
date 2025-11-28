package com.licensemanagement.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.licensemanagement.app.R
import com.licensemanagement.app.databinding.ActivityMainBinding
import com.licensemanagement.app.ui.auth.LoginActivity
import com.licensemanagement.app.util.PreferencesManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesManager = PreferencesManager(this)

        // Check if logged in
        if (!preferencesManager.isLoggedIn()) {
            navigateToLogin()
            return
        }

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        // Set up bottom navigation based on user role
        val userRole = preferencesManager.getUserRole()
        if (userRole == "admin") {
            // Admin navigation
            binding.bottomNavigation.menu.clear()
            binding.bottomNavigation.inflateMenu(R.menu.admin_navigation_menu)
            binding.bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_dashboard -> {
                        navController.navigate(R.id.dashboardFragment)
                        true
                    }
                    R.id.nav_customers -> {
                        navController.navigate(R.id.customersFragment)
                        true
                    }
                    R.id.nav_packs -> {
                        navController.navigate(R.id.packsFragment)
                        true
                    }
                    else -> false
                }
            }
        } else {
            // Customer navigation
            binding.bottomNavigation.menu.clear()
            binding.bottomNavigation.inflateMenu(R.menu.customer_navigation_menu)
            binding.bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_subscription -> {
                        navController.navigate(R.id.subscriptionFragment)
                        true
                    }
                    R.id.nav_history -> {
                        // Navigate to history fragment (can be added later)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        preferencesManager.clearAll()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

