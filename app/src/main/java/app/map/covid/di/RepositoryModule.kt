package app.map.covid.di

import app.map.covid.db.CovidCenterDataSource
import app.map.covid.repository.CovidRepository
import app.map.covid.repository.CovidRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideCovidRepository(covidRepository: CovidCenterDataSource): CovidRepository {
        return CovidRepositoryImpl(covidRepository)
    }
}