package app.map.covid.repository

import app.map.covid.db.CovidCenterDataSource
import app.map.covid.model.CentersModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CovidRepositoryImpl @Inject constructor(private val data: CovidCenterDataSource): CovidRepository {
    override fun getAll(): Single<List<CentersModel>> {
        return data.getAll()
    }

    override fun insert(data: List<CentersModel>): Completable {
        return this.data.insert(data)
    }

    override fun deleteAll(): Completable {
        return data.deleteAll()
    }
}