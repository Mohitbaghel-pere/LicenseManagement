package com.licensemanagement.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.licensemanagement.app.R
import com.licensemanagement.app.databinding.FragmentSubscriptionBinding
import com.licensemanagement.app.data.repository.SubscriptionRepository
import com.licensemanagement.app.data.model.CurrentSubscription
import com.licensemanagement.app.util.PreferencesManager
import kotlinx.coroutines.launch

class SubscriptionFragment : Fragment() {
    private var _binding: FragmentSubscriptionBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferencesManager: PreferencesManager
    private val subscriptionRepository = SubscriptionRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubscriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesManager = PreferencesManager(requireContext())

        setupRecyclerView()
        loadCurrentSubscription()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewSubscriptionHistory.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun loadCurrentSubscription() {
        val token = preferencesManager.getToken()
        if (token == null) {
            Toast.makeText(requireContext(), "Not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val result = subscriptionRepository.getCurrentSubscription(token)
                result.getOrElse { exception ->
                    Toast.makeText(requireContext(), "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                    binding.tvNoSubscription?.visibility = View.VISIBLE
                    binding.cardCurrentSubscription?.visibility = View.GONE
                    return@launch
                }.let { response ->
                    if (response.success && response.subscription != null) {
                        displaySubscription(response.subscription)
                    } else {
                        binding.tvNoSubscription?.visibility = View.VISIBLE
                        binding.cardCurrentSubscription?.visibility = View.GONE
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error loading subscription", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displaySubscription(subscription: CurrentSubscription) {
        binding.cardCurrentSubscription?.visibility = View.VISIBLE
        binding.tvNoSubscription?.visibility = View.GONE
        binding.tvSubscriptionPackName?.text = subscription.pack?.name ?: "N/A"
        binding.tvSubscriptionStatus?.text = "Status: ${subscription.status}"
        binding.tvSubscriptionExpiry?.text = "Expires: ${subscription.expiresAt ?: "N/A"}"
        binding.tvSubscriptionValid?.text = if (subscription.isValid) "Valid" else "Invalid"
        binding.tvSubscriptionValid?.setTextColor(
            if (subscription.isValid) 
                resources.getColor(android.R.color.holo_green_dark, null)
            else 
                resources.getColor(android.R.color.holo_red_dark, null)
        )
    }

    private fun setupClickListeners() {
        binding.btnRequestSubscription?.setOnClickListener {
            // Navigate to request subscription screen
            Toast.makeText(requireContext(), "Request subscription feature", Toast.LENGTH_SHORT).show()
        }

        binding.btnDeactivateSubscription?.setOnClickListener {
            deactivateSubscription()
        }
    }

    private fun deactivateSubscription() {
        val token = preferencesManager.getToken()
        if (token == null) {
            Toast.makeText(requireContext(), "Not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val result = subscriptionRepository.deactivateSubscription(token)
                result.getOrElse { exception ->
                    Toast.makeText(requireContext(), "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                    return@launch
                }.let {
                    Toast.makeText(requireContext(), "Subscription deactivated", Toast.LENGTH_SHORT).show()
                    loadCurrentSubscription()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error deactivating subscription", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

