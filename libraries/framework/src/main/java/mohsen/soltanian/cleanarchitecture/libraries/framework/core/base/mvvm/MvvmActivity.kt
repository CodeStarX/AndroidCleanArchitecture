package mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.mvvm

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.core.CoreActivity
import mohsen.soltanian.cleanarchitecture.libraries.framework.extensions.observeFlowStart
import mohsen.soltanian.cleanarchitecture.libraries.framework.extensions.observeLiveData

abstract class MvvmActivity<VB : ViewDataBinding, VM : MvvmViewModel> : CoreActivity() {

    lateinit var binding: VB

    abstract val viewModel: VM

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    protected abstract fun bindingVariables(): HashMap<Int,Any>

    open fun showProgress() {}

    open fun hideProgress() {}

    open fun showError(throwable: Throwable) {}

    protected fun getBinging(): VB =
        binding

    override fun performDataBinding(layoutID: Int) {
        super.performDataBinding(layoutID)
        if (::binding.isInitialized.not()) {
            binding = DataBindingUtil.setContentView(this, layoutID)
            for (item in bindingVariables()) {
                binding.setVariable(item.key, item.value)
            }
            binding.lifecycleOwner = this
            binding.executePendingBindings()
        }

    }

    override fun observeState() {
        super.observeState()
        observeConnectionState()
        observeProgress()
        observeError()
    }

    private fun observeProgress() {
        observeFlowStart(viewModel.progress) { state ->
            state?.let {
                when (it) {
                   true -> {
                       showProgress()
                   }
                    false -> {
                        hideProgress()

                    }
                }
            }
        }
    }

    private fun observeConnectionState() {
        observeFlowStart(viewModel.connectionState) { state ->
            state?.let {
                when(it) {
                    true -> {
                        networkAvailable()
                    }
                    false -> {
                        networkNotAvailable()
                    }
                }
            }

        }
    }

    private fun observeError() {
        observeLiveData(viewModel.error, ::showError)
    }
}
