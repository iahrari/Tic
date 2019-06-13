package ir.imanahrari.tic.service.database

import androidx.room.*
import ir.imanahrari.tic.service.model.ClassModel

@Dao
interface DaoClassInterface{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(c: ClassModel)

    @Query("select * from extraClasses")
    fun getAll(): List<ClassModel>

    @Query("delete from extraClasses")
    fun deleteAll()

    @Delete
    fun delete(l: ClassModel)
}