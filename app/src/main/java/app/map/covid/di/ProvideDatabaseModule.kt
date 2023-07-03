package app.map.covid.di

import android.content.Context
import androidx.room.Room
import app.map.covid.db.CovidCenterDataSource
import app.map.covid.db.CovidCenterDataSourceImpl
import app.map.covid.db.CovidDao
import app.map.covid.db.CovidDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProvideDatabaseModule {

    /** Provide Room Database */
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CovidDatabase {
        return Room.databaseBuilder(
            context, CovidDatabase::class.java, "covid.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideCovidCenterDataSource(covidDao: CovidDao): CovidCenterDataSource {
        return CovidCenterDataSourceImpl(covidDao)
    }

    @Singleton
    @Provides
    fun provideCovidDao(db: CovidDatabase): CovidDao = db.covidDao()
}