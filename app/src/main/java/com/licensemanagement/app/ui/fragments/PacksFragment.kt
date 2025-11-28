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
import com.licensemanagement.app.databinding.FragmentPacksBinding
import com.licensemanagement.app.ui.components.SubscriptionPackAdapter
import com.licensemanagement.app.util.PreferencesManager
import kotlinx.coroutines.launch

class PacksFragment : Fragment() {
    private var _binding: FragmentPacksBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPacksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesManager = PreferencesManager(requireContext())

        setupRecyclerView()
        loadPacks()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewPacks.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun loadPacks() {
        val token = preferencesManager.getToken()
        if (token == null) {
            Toast.makeText(requireContext(), "Not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.getSubscriptionPacks("Bearer $token", 1, 100)
                if (response.isSuccessful && response.body() != null) {
                    val packs = response.body()!!.packs
                    if (packs != null && packs.isNotEmpty()) {
                        binding.recyclerViewPacks.adapter = SubscriptionPackAdapter(packs) { pack ->
                            Toast.makeText(requireContext(), "Selected: ${pack.name}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "No subscription packs found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load packs", Toast.LENGTH_SHORT).show()
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
