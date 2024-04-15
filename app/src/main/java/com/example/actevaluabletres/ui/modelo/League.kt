package com.example.actevaluabletres.ui.modelo

data class League(val idLeague: String, val strLeague: String, val strSport: String)

data class LeaguesResponse(val leagues: List<League>)
