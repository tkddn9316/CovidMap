package app.map.covid

import android.app.Application

class MyApplication : Application() {

    public companion object {
        public var myApplication: MyApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        myApplication = this
    }
}