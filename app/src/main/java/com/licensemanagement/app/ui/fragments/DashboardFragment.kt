package com.licensemanagement.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.licensemanagement.app.R
import com.licensemanagement.app.databinding.FragmentDashboardBinding
import com.licensemanagement.app.data.api.ApiClient
import com.licensemanagement.app.util.PreferencesManager
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesManager = PreferencesManager(requireContext())

        loadDashboard()
    }

    private fun loadDashboard() {
        val token = preferencesManager.getToken()
        if (token == null) {
            Toast.makeText(requireContext(), "Not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.getDashboard("Bearer $token")
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!.data
                    if (data != null) {
                        binding.tvTotalCustomers?.text = "Total Customers: ${data.totalCustomers}"
                        binding.tvActiveSubscriptions?.text = "Active Subscriptions: ${data.activeSubscriptions}"
                        binding.tvPendingRequests?.text = "Pending Requests: ${data.pendingRequests}"
                        binding.tvTotalRevenue?.text = "Total Revenue: $${String.format("%.2f", data.totalRevenue)}"
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load dashboard", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

