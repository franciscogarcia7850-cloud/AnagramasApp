package com.example.anagramas.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.anagramas.data.MAX_NO_OF_WORDS
import com.example.anagramas.data.SCORE_INCREASE
import com.example.anagramas.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {

    // Estado privado que se modifica aquí y estado público de solo lectura para la UI
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var userGuess by mutableStateOf("")
        private set

    private var currentWord = ""
    private val usedWords = mutableSetOf<String>()

    init {
        resetGame()
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

    fun checkUserGuess() {
        if (userGuess.trim().equals(currentWord, ignoreCase = true)) {
            updateGameState(_uiState.value.score + SCORE_INCREASE)
        } else {
            _uiState.update { it.copy(isGuessedWordWrong = true) }
        }
        updateUserGuess("")
    }

    fun skipWord() {
        updateGameState(_uiState.value.score)
        updateUserGuess("")
    }

    // Pasa a la siguiente palabra o termina el juego
    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS) {
            _uiState.update {
                it.copy(isGuessedWordWrong = false, score = updatedScore, isGameOver = true)
            }
        } else {
            val newWord = pickRandomWordAndShuffle()
            _uiState.update {
                it.copy(
                    currentScrambledWord = newWord,
                    currentWordCount = it.currentWordCount + 1,
                    score = updatedScore,
                    isGuessedWordWrong = false
                )
            }
        }
    }

    private fun pickRandomWordAndShuffle(): String {
        currentWord = allWords.filter { it !in usedWords }.random()
        usedWords.add(currentWord)

        var shuffled: String
        do {
            shuffled = currentWord.toList().shuffled().joinToString("")
        } while (shuffled == currentWord)
        return shuffled
    }
}
