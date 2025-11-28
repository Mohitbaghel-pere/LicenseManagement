package com.licensemanagement.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.licensemanagement.app.data.api.ApiClient
import com.licensemanagement.app.databinding.FragmentCustomersBinding
import com.licensemanagement.app.util.PreferencesManager
import kotlinx.coroutines.launch

class CustomersFragment : Fragment() {
    private var _binding: FragmentCustomersBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesManager = PreferencesManager(requireContext())

        loadCustomers()
    }

    private fun loadCustomers() {
        val token = preferencesManager.getToken()
        if (token == null) {
            Toast.makeText(requireContext(), "Not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.getCustomers("Bearer $token", 1, 10, null)
                if (response.isSuccessful && response.body() != null) {
                    val customers = response.body()!!.customers
                    if (customers != null && customers.isNotEmpty()) {
                        binding.tvCustomersList.text = "Found ${customers.size} customers"
                    } else {
                        binding.tvCustomersList.text = "No customers found"
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load customers", Toast.LENGTH_SHORT).show()
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
