package app.map.covid.domain.di

import app.map.covid.data.db.CovidCenterDataSource
import app.map.covid.domain.repository.CovidRepository
import app.map.covid.domain.repository.CovidRepositoryImpl
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