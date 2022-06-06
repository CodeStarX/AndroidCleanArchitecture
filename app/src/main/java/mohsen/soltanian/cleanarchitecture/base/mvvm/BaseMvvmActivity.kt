package mohsen.soltanian.cleanarchitecture.base.mvvm

import androidx.databinding.ViewDataBinding
import mohsen.soltanian.cleanarchitecture.libraries.framework.component.ProgressDialog
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.mvvm.MvvmActivity
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.mvvm.MvvmViewModel
import mohsen.soltanian.cleanarchitecture.libraries.framework.extensions.showSnackBar
import timber.log.Timber

abstract class BaseMvvmActivity<VB : ViewDataBinding, VM : MvvmViewModel> :
    MvvmActivity<VB, VM>() {

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