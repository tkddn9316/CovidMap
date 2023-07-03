package app.map.covid.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.map.covid.data.model.CentersModel

@Database(entities = [CentersModel::class], version = 2, exportSchema = false)
abstract class CovidDatabase : RoomDatabase() {
    abstract fun covidDao(): CovidDao
}