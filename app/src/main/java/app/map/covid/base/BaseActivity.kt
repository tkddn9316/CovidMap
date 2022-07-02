package app.map.covid.base

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.os.SystemClock
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import app.map.covid.BR
import app.map.covid.R
import app.map.covid.util.OnSingleClickListener

abstract class BaseActivity<VDB : ViewDataBinding, VM : BaseViewModel> :
    AppCompatActivity(), OnSingleClickListener {
    lateinit var binding: VDB
    lateinit var viewModel: VM
    lateinit var viewModelFactory : ViewModelProvider.AndroidViewModelFactory
    lateinit var context: Context
    private var lastClickTime = 0L

    protected abstract fun setup()
    protected abstract fun onCreateView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        context = this
        viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        setup()
        onCreateView(savedInstanceState)
    }

    fun setBinding(@LayoutRes layoutId: Int, modelClass : Class<VM>) {
        binding = DataBindingUtil.setContentView(this, layoutId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(modelClass)
        binding.setVariable(BR.view, this)
        binding.setVariable(BR.vm, viewModel)
        binding.lifecycleOwner = this
    }

    override fun onSingleClick(v: View) {
        if(v.id == R.id.left_){
            finish()
        }
    }

    override fun onItemClick(v: View) {

    }

    override fun onClick(v: View?) {
        val currentClickTime  = SystemClock.uptimeMillis()
        val elapsedTime  = currentClickTime - lastClickTime
        lastClickTime = currentClickTime
        //duplicate
        if(elapsedTime <= OnSingleClickListener.CLICK_INTERVAL){
            return
        }
        onSingleClick(v!!)

    }
}