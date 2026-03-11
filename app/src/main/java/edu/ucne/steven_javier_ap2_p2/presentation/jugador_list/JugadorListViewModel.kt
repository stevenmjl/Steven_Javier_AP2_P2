package edu.ucne.steven_javier_ap2_p2.presentation.jugador_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.steven_javier_ap2_p2.domain.use_case.GetJugadoresUseCase
import edu.ucne.steven_javier_ap2_p2.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JugadorListViewModel @Inject constructor(
    private val getJugadoresUseCase: GetJugadoresUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(JugadorListUiState())
    val state = _state.asStateFlow()

    init {
        loadJugadores()
    }

    fun onEvent(event: JugadorListEvent) {
        when (event) {
            is JugadorListEvent.Refresh -> {
                loadJugadores()
            }
        }
    }

    private fun loadJugadores() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = getJugadoresUseCase()

            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            jugadores = result.data ?: emptyList()
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }
}
