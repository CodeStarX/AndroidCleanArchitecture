package mohsen.soltanian.cleanarchitecture.base.mvi

import androidx.databinding.ViewDataBinding
import mohsen.soltanian.cleanarchitecture.libraries.framework.component.ProgressDialog
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.mvi.MviActivity
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.mvi.MviViewModel
import mohsen.soltanian.cleanarchitecture.libraries.framework.extensions.showSnackBar
import timber.log.Timber

abstract class BaseMviActivity<VB : ViewDataBinding, STATE, VM : MviViewModel<STATE, *>> :
    MviActivity<VB, STATE, VM>() {

    private var progressDialog: ProgressDialog? = null

    override fun showProgress() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this)
        }
        progressDialog?.show()
    }

    override fun hideProgress() {
        progressDialog?.dismiss()
    }

    override fun networkAvailable() {
        super.networkAvailable()
        Timber.tag("ConnectionState").e("connection state is: true")

    }

    override fun networkNotAvailable() {
        super.networkNotAvailable()
        Timber.tag("ConnectionState").e("connection state is: false")

    }

    override fun showError(throwable: Throwable) {
        handleErrorMessage(throwable.message.toString())
    }

    protected open fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank()) return
        hideProgress()
        Timber.e(message)
        showSnackBar(binding.root, message)
    }
}