package edu.ucne.steven_javier_ap2_p2.presentation.jugador_entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.steven_javier_ap2_p2.domain.model.Jugador
import edu.ucne.steven_javier_ap2_p2.domain.use_case.GetJugadorUseCase
import edu.ucne.steven_javier_ap2_p2.domain.use_case.SaveJugadorUseCase
import edu.ucne.steven_javier_ap2_p2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JugadorEntryViewModel @Inject constructor(
    private val getJugadorUseCase: GetJugadorUseCase,
    private val saveJugadorUseCase: SaveJugadorUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(JugadorEntryUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: JugadorEntryEvent) {
        when (event) {
            is JugadorEntryEvent.OnNombresChange -> {
                _state.update { it.copy(nombres = event.nombres, nombresError = null) }
            }
            is JugadorEntryEvent.OnEmailChange -> {
                _state.update { it.copy(email = event.email, emailError = null) }
            }
            is JugadorEntryEvent.Save -> {
                saveJugador()
            }
        }
    }

    fun loadJugador(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = getJugadorUseCase(id)
            when (result) {
                is Resource.Success -> {
                    val jugador = result.data
                    _state.update {
                        it.copy(
                            isLoading = false,
                            jugadorId = jugador?.jugadorId,
                            nombres = jugador?.nombres ?: "",
                            email = jugador?.email ?: ""
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.message) }
                }
                is Resource.Loading -> { }
            }
        }
    }

    private fun saveJugador() {
        viewModelScope.launch {
            if (_state.value.nombres.isBlank()) {
                _state.update { it.copy(nombresError = "El nombre es requerido") }
                return@launch
            }

            _state.update { it.copy(isLoading = true) }
            val jugador = Jugador(
                jugadorId = _state.value.jugadorId ?: 0,
                nombres = _state.value.nombres,
                email = _state.value.email
            )

            val result = if (jugador.jugadorId == 0) {
                saveJugadorUseCase(jugador)
            } else {
                val updateResult = saveJugadorUseCase.update(jugador.jugadorId, jugador)
                if (updateResult is Resource.Success) Resource.Success(jugador)
                else Resource.Error(updateResult.message ?: "Error al actualizar")
            }

            when (result) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false, isSuccess = true) }
                }
                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.message) }
                }
                is Resource.Loading -> { }
            }
        }
    }
}