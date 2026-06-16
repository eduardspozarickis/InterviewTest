package com.betsson.interviewtest.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.betsson.interviewtest.domain.entity.Bet
import com.betsson.interviewtest.domain.usecase.FetchBetsListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val ioDispatcher: CoroutineDispatcher,
    private val fetchBetsListUseCase: FetchBetsListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val bets = withContext(ioDispatcher) {
                fetchBetsListUseCase.launch()
            }

            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    bets = bets
                )
            }
        }
    }

    fun onUpdateOddsClicked() {
        _uiState.update { currentState ->
            val updatedBets = calculateOdds(currentState.bets)
            currentState.copy(bets = updatedBets)
        }
    }

    private fun calculateOdds(bets: List<Bet>): List<Bet> {
        return bets.map { originalBet ->
            var newOdds = originalBet.odds
            var newSellIn = originalBet.sellIn

            if (originalBet.type != "Total score" && originalBet.type != "Number of fouls") {
                if (newOdds > 0 && originalBet.type != "First goal scorer") {
                    newOdds -= 1
                }
            } else {
                if (newOdds < 50) {
                    newOdds += 1
                    if (originalBet.type == "Number of fouls") {
                        if (newSellIn < 11 && newOdds < 50) newOdds += 1
                        if (newSellIn < 6 && newOdds < 50) newOdds += 1
                    }
                }
            }

            if (originalBet.type != "First goal scorer") {
                newSellIn -= 1
            }

            if (newSellIn < 0) {
                if (originalBet.type != "Total score") {
                    if (originalBet.type != "Number of fouls") {
                        if (newOdds > 0 && originalBet.type != "First goal scorer") {
                            newOdds -= 1
                        }
                    } else {
                        newOdds = 0
                    }
                } else {
                    if (newOdds < 50) {
                        newOdds += 1
                    }
                }
            }

            originalBet.copy(odds = newOdds, sellIn = newSellIn)
        }
    }

    data class HomeUiState(
        val isLoading: Boolean = false,
        val bets: List<Bet> = emptyList(),
    )
}