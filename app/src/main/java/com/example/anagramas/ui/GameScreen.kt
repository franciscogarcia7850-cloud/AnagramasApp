package com.example.anagramas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anagramas.R
import com.example.anagramas.data.MAX_NO_OF_WORDS

@Composable
fun GameScreen(
    playerName: String,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel()
) {
    val gameUiState by gameViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.player, playerName))
        Text(stringResource(R.string.score, gameUiState.score))

        GameLayout(
            currentScrambledWord = gameUiState.currentScrambledWord,
            wordCount = gameUiState.currentWordCount,
            userGuess = gameViewModel.userGuess,
            isGuessWrong = gameUiState.isGuessedWordWrong,
            onUserGuessChanged = { gameViewModel.updateUserGuess(it) },
            onKeyboardDone = { gameViewModel.checkUserGuess() }
        )

        Button(
            onClick = { gameViewModel.checkUserGuess() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.submit))
        }
        OutlinedButton(
            onClick = { gameViewModel.skipWord() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.skip))
        }
    }

    if (gameUiState.isGameOver) {
        FinalScoreDialog(
            playerName = playerName,
            score = gameUiState.score,
            onPlayAgain = { gameViewModel.resetGame() },
            onExit = onExit
        )
    }
}

@Composable
private fun GameLayout(
    currentScrambledWord: String,
    wordCount: Int,
    userGuess: String,
    isGuessWrong: Boolean,
    onUserGuessChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit
) {
    Text(stringResource(R.string.word_count, wordCount, MAX_NO_OF_WORDS))
    Text(
        text = currentScrambledWord,
        style = MaterialTheme.typography.displayMedium
    )
    Text(stringResource(R.string.instructions))
    OutlinedTextField(
        value = userGuess,
        onValueChange = onUserGuessChanged,
        singleLine = true,
        isError = isGuessWrong,
        label = {
            Text(
                stringResource(
                    if (isGuessWrong) R.string.wrong_guess else R.string.enter_your_word
                )
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onKeyboardDone() }),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun FinalScoreDialog(
    playerName: String,
    score: Int,
    onPlayAgain: () -> Unit,
    onExit: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(stringResource(R.string.game_over)) },
        text = { Text(stringResource(R.string.you_scored, playerName, score)) },
        dismissButton = {
            TextButton(onClick = onExit) { Text(stringResource(R.string.exit)) }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) { Text(stringResource(R.string.play_again)) }
        }
    )
}
