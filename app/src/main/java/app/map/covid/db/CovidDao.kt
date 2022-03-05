package app.map.covid.db

import androidx.room.*
import app.map.covid.retrofit.CentersModel

@Dao
interface CovidDao {
    @Query("SELECT * FROM CovidCenterTable")
    fun getAll(): List<CentersModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(centersModel: CentersModel)

    @Delete
    fun delete(covidEntity: CentersModel)
}