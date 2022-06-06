package mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.mvi

import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.mvvm.MvvmViewModel
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.flow.MutableEventFlow
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.flow.asEventFlow

abstract class MviViewModel<STATE, EVENT> : MvvmViewModel() {

    private val _stateFlow = MutableEventFlow<STATE>()
    val stateFlow = _stateFlow.asEventFlow()

    abstract fun onTriggerEvent(eventType: EVENT)

    protected fun setState(state: STATE) = safeLaunch {
        _stateFlow.emit(state)
    }
}
