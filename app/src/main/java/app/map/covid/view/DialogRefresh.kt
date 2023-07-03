package app.map.covid.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import app.map.covid.R
import app.map.covid.databinding.DialogRefreshBinding
import app.map.covid.util.Utils.hideKeyboard

class DialogRefresh(context: Context, val onClickListener: (String) -> Unit) :
    Dialog(context, R.style.Dialog), View.OnClickListener {

    lateinit var binding: DialogRefreshBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate<DialogRefreshBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_refresh, null, false
        ).apply {
            setContentView(root)
            view = this@DialogRefresh
        }
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        setCanceledOnTouchOutside(false)
    }

    override fun onClick(v: View) {
        binding.count.hideKeyboard()
        dismiss().also {
            if (v.id == R.id.left_) {
                onClickListener(binding.count.text.toString())
            }
        }
    }
}