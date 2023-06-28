package app.map.covid.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.map.covid.model.CentersModel

@Database(entities = [CentersModel::class], version = 2, exportSchema = false)
abstract class CovidDatabase : RoomDatabase() {
    abstract fun covidDao(): CovidDao
}