package com.licensemanagement.app.ui.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.licensemanagement.app.R
import com.licensemanagement.app.data.model.SubscriptionPack

class SubscriptionPackAdapter(
    private val packs: List<SubscriptionPack>,
    private val onItemClick: (SubscriptionPack) -> Unit
) : RecyclerView.Adapter<SubscriptionPackAdapter.PackViewHolder>() {

    class PackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvPackName)
        val description: TextView = itemView.findViewById(R.id.tvPackDescription)
        val price: TextView = itemView.findViewById(R.id.tvPackPrice)
        val validity: TextView = itemView.findViewById(R.id.tvPackValidity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subscription_pack, parent, false)
        return PackViewHolder(view)
    }

    override fun onBindViewHolder(holder: PackViewHolder, position: Int) {
        val pack = packs[position]
        holder.name.text = pack.name
        holder.description.text = pack.description
        holder.price.text = "$${pack.price}"
        holder.validity.text = "${pack.validityMonths} months"

        holder.itemView.setOnClickListener {
            onItemClick(pack)
        }
    }

    override fun getItemCount() = packs.size
}

