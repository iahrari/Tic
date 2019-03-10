package ir.imanahrari.tic.service.model
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.jsoup.select.Elements

@Entity(tableName = "absents")
class LessonModel {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
    var allAbsents: Int = 0
    var name: String? = null
    var status: String? = null
    var absentsListS: String = ""
    var absentsLimit: Int? = null
    var acceptableAbsents: Int = 0
    var unacceptableAbsents: Int = 0

    @Ignore
    var absentsList:List<String>? = null

    fun setUnacceptableAbsents(data: String){ unacceptableAbsents = data.toInt() }
    fun setAcceptableAbsents(data: String){ acceptableAbsents = data.toInt() }
    fun setAbsentsLimit(data: String){ absentsLimit = data.toInt() }
    fun setAllAbsents(data: String){ allAbsents = data.toInt() }

    fun setAbsentsList(columns: Elements){
        val data : MutableList<String> = ArrayList()

        for (column in columns)
            for (span in column.select("span"))
                data.add(span.text())

        absentsList = data
        setAbsentsLS()
    }

    private fun setAbsentsLS(){
        for(s in absentsList!!)
            absentsListS += "$s,"
    }

    fun getAbsentsL(): List<String>{
        if(absentsList.isNullOrEmpty())
            absentsList = absentsListS.split(",")

        return absentsList!!
    }
}