package app.map.covid

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import app.map.covid.util.FLog

class FApplication : MultiDexApplication(), ActivityLifecycleCallbacks {

    override fun onCreate() {
        FLog.e("onCreate")
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}