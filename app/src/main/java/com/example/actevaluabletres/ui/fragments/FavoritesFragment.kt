package com.example.actevaluabletres.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actevaluabletres.databinding.FragmentFavoritesBinding
import com.example.actevaluabletres.ui.adaptadores.TeamAdapter
import com.example.actevaluabletres.ui.util.PreferencesUtil

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        loadFavorites()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFavorites()
    }

    override fun onResume() {
        super.onResume()
        loadFavorites()
    }

    private fun loadFavorites() {
        val favorites = PreferencesUtil.loadFavorites(requireContext()).toMutableList()
        if (binding.recyclerFavorites.layoutManager == null) {
            binding.recyclerFavorites.layoutManager = LinearLayoutManager(context)
        }
        binding.recyclerFavorites.adapter = TeamAdapter(favorites) { team, isFavorite ->
            if (!isFavorite) {
                PreferencesUtil.saveFavorites(requireContext(), favorites.filter { it.isFavorite })
            }
        }
    }
}



