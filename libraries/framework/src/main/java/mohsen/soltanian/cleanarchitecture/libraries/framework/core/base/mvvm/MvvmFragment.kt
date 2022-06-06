package mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.core.CoreFragment
import mohsen.soltanian.cleanarchitecture.libraries.framework.extensions.observeFlowStart
import mohsen.soltanian.cleanarchitecture.libraries.framework.extensions.observeLiveData

abstract class MvvmFragment<VB : ViewDataBinding, VM : MvvmViewModel> : CoreFragment() {

    var binding: VB? = null

    abstract val viewModel: VM

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    protected abstract fun bindingVariables(): HashMap<Int, Any>

    open fun showProgress() {}

    open fun hideProgress() {}

    open fun observeUi() {}

    open fun showError(throwable: Throwable) {}

    abstract fun onViewReady(bundle: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return persistentView(inflater, layoutResId, container)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstTimeCreated(savedInstanceState = savedInstanceState))
            onViewReady(savedInstanceState)

        observeState()
        observeUi()
    }

    private fun observeState() {
        observeConnectionState()
        observeProgress()
        observeError()
    }

    private fun observeConnectionState() {
        observeFlowStart(viewModel.connectionState) { state ->
            state?.let {
                when (it) {
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

    private fun observeProgress() {
        observeFlowStart(viewModel.progress) { state ->
            state?.let {
                if (it) {
                    showProgress()
                } else {
                    hideProgress()
                }
            }
        }
    }

    private fun observeError() {
        observeLiveData(viewModel.error, ::showError)
    }

    private fun persistentView(
        inflater: LayoutInflater,
        layout: Int,
        container: ViewGroup?
    ): View? {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(inflater, layout, container, false)
            for (item in bindingVariables()) {
                binding?.setVariable(item.key, item.value)
            }
            binding?.lifecycleOwner = this
            binding?.executePendingBindings()
            rootView = binding?.root
        } else {
            (rootView?.parent as? ViewGroup)?.removeView(rootView)
        }
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    protected fun getBinging(): VB? =
        binding
}
