package app.map.covid.db

import androidx.room.*
import app.map.covid.model.CentersModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface CovidDao {
    @Query("SELECT * FROM CovidCenterTable")
    fun getAll(): Single<List<CentersModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<CentersModel>): Completable

    @Query("DELETE FROM CovidCenterTable")
    fun deleteAll(): Completable
}