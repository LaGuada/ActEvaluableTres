package com.example.actevaluabletres.ui.adaptadores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.actevaluabletres.R
import com.example.actevaluabletres.databinding.ItemTeamBinding
import com.example.actevaluabletres.ui.modelo.Team
class TeamAdapter(
    private var teams: MutableList<Team>,  // Cambiar a MutableList para modificar la lista directamente
    private val onFavoriteClicked: (Team, Boolean) -> Unit
) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    inner class TeamViewHolder(private val binding: ItemTeamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(team: Team) {
            binding.textViewTeamName.text = team.strTeam
            Glide.with(binding.root.context).load(team.strTeamBadge).into(binding.imageViewTeamBadge)
            updateFavoriteIcon(team.isFavorite)

            binding.imageViewFavorite.setOnClickListener {
                team.isFavorite = !team.isFavorite
                updateFavoriteIcon(team.isFavorite)
                onFavoriteClicked(team, team.isFavorite)  // Pasamos el estado actualizado
                if (!team.isFavorite) {
                    teams.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
            }
        }

        private fun updateFavoriteIcon(isFavorite: Boolean) {
            if (isFavorite) {
                binding.imageViewFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.imageViewFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }

    fun updateData(newTeams: List<Team>) {
        teams.clear()
        teams.addAll(newTeams)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teams[position])
    }

    override fun getItemCount() = teams.size
}
