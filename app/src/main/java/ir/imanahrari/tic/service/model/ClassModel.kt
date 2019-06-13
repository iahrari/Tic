package ir.imanahrari.tic.service.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "extraClasses")
class ClassModel(@PrimaryKey(autoGenerate = false) var id :Int, var lesson: String, var date: String, var teacher: String)