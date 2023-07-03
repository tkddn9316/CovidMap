package app.map.covid.data.db

import app.map.covid.data.model.CentersModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CovidCenterDataSourceImpl @Inject constructor(private val covidDao: CovidDao):
    CovidCenterDataSource {
    override fun getAll(): Single<List<CentersModel>> {
        return covidDao.getAll()
    }

    override fun insert(data: List<CentersModel>): Completable {
        return covidDao.insert(data)
    }

    override fun deleteAll(): Completable {
        return covidDao.deleteAll()
    }
}