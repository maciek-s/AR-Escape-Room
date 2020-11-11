package com.masiad.arescaperoom.ui.game

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masiad.arescaperoom.gamelogic.GamePhase
import com.masiad.arescaperoom.gamelogic.Level
import com.masiad.arescaperoom.gamelogic.LevelManager
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

    private val _level by lazy { MutableLiveData<Level>() }
    val level: LiveData<Level>
        get() = _level

    val instructionName: String
        get() {
            requireNotNull(level.value) { "getInstructionName() called but level not loaded" }
            return level.value?.instructionName ?: ""
        }

    fun loadLevel(levelNumber: Int) {
        viewModelScope.launch {
            setLoadingProgress(50)
            _level.value = levelManager.loadLevel(levelNumber)
        }
    }

    private fun switchPhase(phase: GamePhase) {
        _gamePhase.value = phase
    }

    fun informInstructionAlertClosed() {
        switchPhase(GamePhase.PLACING)
    }

    fun informPreparingEnded() {
        viewModelScope.launch {
            setLoadingProgress(100)
            delay(1000)
            switchPhase(GamePhase.GAME_LOADED)
        }
    }

    fun informOnSceneClicked(hasAnchor: Boolean) {
        if (hasAnchor) {
            switchPhase(GamePhase.PLACED)
        }
    }
}