package com.masiad.arescaperoom.ui.game

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masiad.arescaperoom.gamelogic.GamePhase
import com.masiad.arescaperoom.gamelogic.LevelManager
import com.masiad.arescaperoom.gamelogic.levels.Level
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel @ViewModelInject constructor(
    private val levelManager: LevelManager
) : ViewModel() {

    private val _gamePhase by lazy { MutableLiveData(GamePhase.LOADING) }
    val gamePhase: LiveData<GamePhase>
        get() = _gamePhase

    val loadingProgress = MutableLiveData(0)
    fun setLoadingProgress(progress: Int) {
        loadingProgress.value = progress
    }

    private lateinit var level: Level

    fun loadLevel(levelNumber: Int) {
        viewModelScope.launch {
            level = levelManager.loadLevel(levelNumber)
            setLoadingProgress(100)
            delay(250)
            switchPhase(GamePhase.GAME_LOADED)
        }
    }

    private fun switchPhase(phase: GamePhase) {
        _gamePhase.value = phase
    }

    fun informInstructionAlertClosed() {
        switchPhase(GamePhase.PLACING)
    }
}