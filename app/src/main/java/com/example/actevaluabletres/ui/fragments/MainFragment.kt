package com.example.actevaluabletres.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actevaluabletres.R
import com.example.actevaluabletres.databinding.FragmentMainBinding
import com.example.actevaluabletres.ui.adapters.LeagueAdapter
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nombreUsuario = arguments?.getString("nombreUsuario") ?: "Usuario Anónimo"
        binding.textoMain.text = "Bienvenido $nombreUsuario"
        setupRecyclerView()
        loadLeagues()
    }

    private fun setupRecyclerView() {
        binding.recyclerUsuarios.layoutManager = LinearLayoutManager(context)
    }

    private fun loadLeagues() {
        Thread {
            val url = URL("https://www.thesportsdb.com/api/v1/json/3/all_leagues.php")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = StringBuilder()
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }

            parseLeagues(response.toString())  // Método para parsear JSON y actualizar UI
            connection.disconnect()
        }.start()
    }

    private fun parseLeagues(jsonString: String) {
        val jsonObject = JSONObject(jsonString)
        val leaguesArray = jsonObject.getJSONArray("leagues")
        val leagueNames = mutableListOf<String>()

        for (i in 0 until leaguesArray.length()) {
            val league = leaguesArray.getJSONObject(i)
            if (league.getString("strSport") == "Soccer") {
                leagueNames.add(league.getString("strLeague"))
            }
        }

        activity?.runOnUiThread {
            binding.recyclerUsuarios.adapter = LeagueAdapter(leagueNames) { leagueName ->
                navigateToLeague(leagueName)
            }
        }
    }
    private fun navigateToLeague(leagueName: String) {
        val bundle = Bundle()
        bundle.putString("leagueName", leagueName)  //  la clave coincide con la definida en el NavGraph para el argumento.

        findNavController().navigate(R.id.action_mainFragment_to_leagueFragment, bundle)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}









