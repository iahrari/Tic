package ir.imanahrari.tic.service.database

import androidx.room.*
import ir.imanahrari.tic.service.model.LessonModel

@Dao
interface DAOInterface {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(l: LessonModel)

    @Query("select * from absents")
    fun getAll(): List<LessonModel>

    @Query("delete from absents")
    fun deleteAll()

    @Delete
    fun delete(l: LessonModel)
}