package app.map.covid.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.map.covid.retrofit.CentersModel

@Database(entities = [CentersModel::class], version = 1, exportSchema = false)
abstract class CovidDatabase : RoomDatabase() {
    abstract fun covidDao(): CovidDao
}