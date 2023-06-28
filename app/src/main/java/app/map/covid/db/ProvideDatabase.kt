package app.map.covid.db

import android.content.Context
import androidx.room.Room

private var instance: CovidDatabase? = null

fun provideCovidDao(context: Context): CovidDao = provideDatabase(context).covidDao()

private fun provideDatabase(context: Context): CovidDatabase {
    if (null == instance) {
        // 싱글톤 패턴을 사용하여 최초 1회만 생성합니다.
        instance =
            Room.databaseBuilder(context.applicationContext, CovidDatabase::class.java, "covid.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }

    return instance!!
}