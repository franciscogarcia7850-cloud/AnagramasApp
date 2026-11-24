package com.example.anagramas

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class Game(val playerName: String)
