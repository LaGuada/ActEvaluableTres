package com.example.actevaluabletres.ui.modelo

data class Team(
    val idTeam: String,
    val strTeam: String,
    val strTeamBadge: String, // URL de la imagen del equipo
    var isFavorite: Boolean = false // Añade esto
)

