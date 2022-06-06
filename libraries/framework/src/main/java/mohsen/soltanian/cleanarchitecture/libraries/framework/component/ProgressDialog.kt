package mohsen.soltanian.cleanarchitecture.libraries.framework.component

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import mohsen.soltanian.cleanarchitecture.libraries.framework.databinding.DialogProgressBinding

class ProgressDialog(context: Context) : Dialog(context) {
    init {
        val binding = DialogProgressBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        setCanceledOnTouchOutside(false)
        setCancelable(true)
        setContentView(binding.root)
    }
}