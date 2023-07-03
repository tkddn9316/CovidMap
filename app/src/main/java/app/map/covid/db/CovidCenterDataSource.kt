package app.map.covid.db

import app.map.covid.model.CentersModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface CovidCenterDataSource {
    fun getAll(): Single<List<CentersModel>>
    fun insert(data: List<CentersModel>): Completable
    fun deleteAll(): Completable
}