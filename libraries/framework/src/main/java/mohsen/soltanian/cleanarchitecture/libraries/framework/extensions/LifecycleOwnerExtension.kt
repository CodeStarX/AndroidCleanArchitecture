package mohsen.soltanian.cleanarchitecture.libraries.framework.extensions

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> LifecycleOwner.observeFlowStart(property: Flow<T>, block: (T) -> Unit) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            property.collect { block(it) }
        }
    }
}

fun <T> LifecycleOwner.observeLiveData(liveData: LiveData<T>, block: (T) -> Unit) {
    liveData.observe(this, Observer { block(it) })
}