package com.masiad.arescaperoom.ui.game

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.masiad.arescaperoom.gamelogic.GamePhase
import com.masiad.arescaperoom.gamelogic.Inventory
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

    val levelNumber: Int
        get() {
            requireNotNull(level.value) { "getInstructionName() called but level not loaded" }
            return level.value!!.number
        }

    // Visible on start to proper laid on
    val isInventoryLayoutVisible by lazy { MutableLiveData(true) }
    val isInventoryLayoutToggle by lazy { MutableLiveData(true) }

    val isMoveButtonVisible by lazy {
        Transformations.map(gamePhase) {
            it == GamePhase.PLACED || it == GamePhase.GAME_STARTED
        }
    }

    private val _inventoryList by lazy { MutableLiveData<List<Inventory>>() }
    val inventoryList: LiveData<List<Inventory>>
        get() = _inventoryList

    var selectedInventory: Inventory? = null

    fun informPostOnViewCreated() {
        // Hide after correctly laid on view
        isInventoryLayoutToggle.value = false
        isInventoryLayoutVisible.value = false
    }

    fun informInventoryToggle() {
        isInventoryLayoutToggle.value?.let {
            isInventoryLayoutToggle.value = !it
        }

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

    fun informGameStarted() {
        isInventoryLayoutVisible.value = true
        switchPhase(GamePhase.GAME_STARTED)
    }

    fun informInventoryPickUp(inventory: Inventory) {
        addInventoryToList(inventory)
    }

    //todo update list
    private fun addInventoryToList(inventory: Inventory) {
        val updated = (inventoryList.value?.toMutableList() ?: mutableListOf()).apply {
            add(inventory)
        }
        _inventoryList.value = updated.toList()
    }

    fun informNodeUnlock(isLocked: Boolean) {
        if (!isLocked) {
            requireNotNull(inventoryList.value) { "informNodeUnlock() was called by inventory list is null" }
            requireNotNull(selectedInventory) { "informNodeUnlock() was called by selected inventory is null" }
            val updated = inventoryList.value?.toMutableList()?.apply {
                remove(selectedInventory)
            }
            _inventoryList.value = updated?.toList()
        }
    }
}