package ir.imanahrari.tic.service.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.imanahrari.tic.service.model.LessonModel

@Database(entities = [LessonModel::class], version = 1, exportSchema = false)
abstract class ADatabase: RoomDatabase() {
    abstract fun getDAO(): DAOInterface

    companion object {
        var INSTANCE: ADatabase? = null
        fun getInstance(context: Context): ADatabase{
            INSTANCE = Room.databaseBuilder(context, ADatabase::class.java, "DB").build()
            return INSTANCE!!
        }
    }
}