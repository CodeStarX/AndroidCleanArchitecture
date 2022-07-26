package mohsen.soltanian.cleanarchitecture.base.mvi

import androidx.databinding.ViewDataBinding
import mohsen.soltanian.cleanarchitecture.libraries.framework.component.ProgressDialog
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.mvi.MviFragment
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.mvi.MviViewModel
import mohsen.soltanian.cleanarchitecture.libraries.framework.extensions.showSnackBar
import timber.log.Timber

abstract class BaseMviFragment<VB : ViewDataBinding, STATE, VM : MviViewModel<STATE, *>> :
    MviFragment<VB, STATE, VM>() {

    private var progressDialog: ProgressDialog? = null


    override fun showProgress() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(requireContext())
        }
        progressDialog?.show()
    }

    override fun hideProgress() {
        progressDialog?.dismiss()
    }

    override fun networkAvailable() {
        super.networkAvailable()
        Timber.tag("ConnectionState").e("connection state is: Network available")

    }

    override fun networkNotAvailable() {
        super.networkNotAvailable()
        Timber.tag("ConnectionState").e("connection state is: Network unavailable")

    }

    override fun showError(throwable: Throwable) {
        handleErrorMessage(throwable.message.toString())
    }

    protected open fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank()) return
        hideProgress()
        Timber.e(message)
        binding?.let { showSnackBar(it.root, message) }
        Result
    }

}