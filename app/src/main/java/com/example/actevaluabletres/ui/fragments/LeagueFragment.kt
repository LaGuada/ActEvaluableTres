package com.example.actevaluabletres.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.actevaluabletres.databinding.FragmentLeagueBinding
import com.example.actevaluabletres.ui.modelo.Team
import com.example.actevaluabletres.ui.adaptadores.TeamAdapter
import com.example.actevaluabletres.ui.util.PreferencesUtil
import org.json.JSONObject

class LeagueFragment : Fragment() {
    private var _binding: FragmentLeagueBinding? = null
    private val binding get() = _binding!!
    private lateinit var teamAdapter: TeamAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLeagueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val leagueName = arguments?.getString("leagueName") ?: "Default League Name"
        setupRecyclerView()
        loadTeams(leagueName)
    }

    private fun setupRecyclerView() {
        teamAdapter = TeamAdapter(mutableListOf()) { team, isFavorite ->
            updateFavorites(team, isFavorite)
        }
        binding.recyclerViewTeams.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = teamAdapter
        }
    }

    private fun loadTeams(leagueName: String) {
        val queue = Volley.newRequestQueue(context)
        val url = "https://www.thesportsdb.com/api/v1/json/3/search_all_teams.php?l=$leagueName"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                handleResponse(response)
            },
            Response.ErrorListener { error ->
                Toast.makeText(context, "Error loading teams: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        queue.add(jsonObjectRequest)
    }

    private fun handleResponse(jsonObject: JSONObject) {
        val teamsArray = jsonObject.getJSONArray("teams")
        val teams = mutableListOf<Team>()
        for (i in 0 until teamsArray.length()) {
            val teamObj = teamsArray.getJSONObject(i)
            val team = Team(
                idTeam = teamObj.getString("idTeam"),
                strTeam = teamObj.getString("strTeam"),
                strTeamBadge = teamObj.getString("strTeamBadge"),
                isFavorite = false // Asumimos que inicialmente no es favorito
            )
            teams.add(team)

        }
        teamAdapter.updateData(teams)
    }

    private fun updateFavorites(team: Team, isFavorite: Boolean) {
            val context = requireContext()
            val favorites = PreferencesUtil.loadFavorites(context).toMutableList()
            if (isFavorite) {
                if (!favorites.any { it.idTeam == team.idTeam }) {
                    favorites.add(team)
                }
            } else {
                favorites.removeAll { it.idTeam == team.idTeam }
            }
            PreferencesUtil.saveFavorites(context, favorites)
        }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

