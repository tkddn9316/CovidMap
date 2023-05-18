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
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import app.map.covid.BR
import app.map.covid.R
import app.map.covid.util.FLog
import app.map.covid.util.OnSingleClickListener

abstract class BaseActivity<VDB : ViewDataBinding, VM : BaseViewModel> :
    AppCompatActivity(), ViewModelStoreOwner, OnSingleClickListener {
    lateinit var binding: VDB
    lateinit var viewModel: VM
    protected lateinit var context: Context
    protected lateinit var activity: BaseActivity<VDB, VM>
    private var lastClickTime = 0L

    protected val TAG: String by lazy {
        javaClass.simpleName
    }

    private val viewModelStores = ViewModelStore()

    protected abstract fun setup()
    protected abstract fun onCreateView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FLog.e(TAG, "onCreate")
        context = this
        activity = this
        setup()
        onCreateView(savedInstanceState)
    }

    override fun onDestroy() {
        FLog.e(TAG, "onDestroy")
        viewModel.onCleared()
        super.onDestroy().apply { viewModelStores.clear() }
    }

    open fun setBinding(@LayoutRes layoutId: Int) {
        binding = DataBindingUtil.setContentView<VDB>(this, layoutId).apply {
            setVariable(BR.viewModel, viewModel)
            setVariable(BR.view, this@BaseActivity)
            lifecycleOwner = this@BaseActivity
        }
    }

    protected fun setBinding(@LayoutRes layoutId: Int, modelClass: Class<VM>) {
        viewModel = FViewModelFactory.getInstance(this).create(modelClass)
        setBinding(layoutId)
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

    protected fun createView(@LayoutRes res: Int): View {
        return View.inflate(context, res, null)
    }
}