package com.example.actevaluabletres.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actevaluabletres.databinding.ItemLeagueBinding

class LeagueAdapter(
    private val leagues: List<String>,
    private val onItemClicked: (String) -> Unit
) : RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder>() {

    class LeagueViewHolder(private val binding: ItemLeagueBinding,
                           private val onItemClicked: (String) -> Unit)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(leagueName: String) {
            binding.textViewLeagueName.text = leagueName
            binding.root.setOnClickListener { onItemClicked(leagueName) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val binding = ItemLeagueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeagueViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bind(leagues[position])
    }

    override fun getItemCount() = leagues.size
}
