package com.jvg.gpsapp.ui.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavOptions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

interface Navigator {
    val startDestination: Destination
        get() = Destination.Root
    val navigationActions: Flow<NavigationAction>
    val stateHandle: SavedStateHandle
        get() = SavedStateHandle()
    val actions: StateFlow<ActionStack>

    suspend fun navigate(
        destination: Destination,
        navOptions: NavOptions? = null,
    )

    suspend fun popUntil(
        destination: Destination,
        inclusive: Boolean = false,
        saveState: Boolean = false
    )

    suspend fun navigateUp()

    fun consumeAction(action: NavigationAction)
}

class DefaultNavigator : Navigator {
    private val _navigationActions: Channel<NavigationAction> = Channel<NavigationAction>()
    override val navigationActions: Flow<NavigationAction> = _navigationActions.receiveAsFlow()

    private val _actionStack: MutableStateFlow<ActionStack> = MutableStateFlow(ActionStack())
    override val actions: StateFlow<ActionStack> =_actionStack.asStateFlow()

    override suspend fun navigate(
        destination: Destination,
        navOptions: NavOptions?,
    ) {
        val action = NavigationAction.Navigate(destination, navOptions)
        if (!_actionStack.value.actions.contains(action)) {
            _actionStack.update { actions ->
                actions.addAction(action)
            }
            _navigationActions.send(action)
        }
    }

    override suspend fun popUntil(
        destination: Destination,
        inclusive: Boolean,
        saveState: Boolean
    ) {
        val action = NavigationAction.PopUntil(destination, inclusive, saveState)
        _navigationActions.send(action)
    }

    override suspend fun navigateUp() {
        val action: NavigationAction.NavigateUp = NavigationAction.NavigateUp
        if (!_actionStack.value.actions.contains(action)) {
            _actionStack.update { actions ->
                actions.addAction(action)
            }
            delay(250)
            _navigationActions.send(action)
        }
    }

    override fun consumeAction(action: NavigationAction) {
        _actionStack.update { state ->
            val actions: MutableList<NavigationAction> = state.actions.toMutableList()
            actions.remove(action)
            state.copy(actions = actions)
        }
    }
}

data class ActionStack(
    val actions: List<NavigationAction> = emptyList()
) {
    fun addAction(action: NavigationAction): ActionStack {
        val actions: MutableList<NavigationAction> = actions.toMutableList()

        if (!actions.contains(action)) {
            actions.add(action)
        }

        return copy(actions = actions)
    }
}
