package app.map.covid.domain.repository

import app.map.covid.data.model.CentersModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface CovidRepository {
    fun getAll(): Single<List<CentersModel>>
    fun insert(data: List<CentersModel>): Completable
    fun deleteAll(): Completable
}