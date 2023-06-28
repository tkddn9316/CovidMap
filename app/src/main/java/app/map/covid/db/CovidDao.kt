package app.map.covid.db

import androidx.room.*
import app.map.covid.model.CentersModel
import io.reactivex.rxjava3.core.Completable

@Dao
interface CovidDao {
    @Query("SELECT * FROM CovidCenterTable")
    fun getAll(): List<CentersModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(centersModel: CentersModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<CentersModel>): Completable

    @Query("DELETE FROM CovidCenterTable")
    fun deleteAll(): Completable
}