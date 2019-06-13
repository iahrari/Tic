package ir.imanahrari.tic.service.utilities

import ir.imanahrari.tic.service.database.ADatabase
import ir.imanahrari.tic.service.model.ClassModel
import ir.imanahrari.tic.service.model.LessonModel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import org.jsoup.select.Elements
import org.jsoup.nodes.Document
import org.jsoup.Jsoup

fun prepareDocument(html: String): Document {
    var ht = html.replace("\\u003C", "<")
    ht = ht.replace("\\n", "")
    ht = ht.replace("\\t", "")
    return Jsoup.parse(ht)
}

fun prepareLessonModel(columns: Elements): LessonModel {
    val data = LessonModel()
    data.id = columns[0].text().toInt() - 1
    data.name = columns[1].text()
    data.setAcceptableAbsents(columns[2].text())
    data.setUnacceptableAbsents(columns[3].text())
    data.setAllAbsents(columns[4].text())
    data.setAbsentsLimit(columns[5].text())
    data.status = columns[6].text()
    data.setAbsentsList(columns)
    return data
}

fun prepareClassMode(columns: Elements): ClassModel {
    val id = columns[0].text().toInt() - 1
    val name = columns[1].text()
    val date = columns[2].text().replace(" -", "").replace(" ", "\n")
    val teacher = columns[3].text()

    return ClassModel(id, name, date, teacher)
}

fun saveToDB(data: List<LessonModel>) = runBlocking {
    withContext(Dispatchers.Default) {
        for(d in data)
            ADatabase.INSTANCE!!.getDAO().insert(d)
    }
}

fun saveClassesToDB(data: List<ClassModel>) = runBlocking {
    withContext(Dispatchers.Default){
        for(d in data)
            ADatabase.INSTANCE!!.getClassDAO().insert(d)
    }
}

fun deleteExtraRowsFromDB(data: List<LessonModel>) = runBlocking {
    withContext(Dispatchers.Default) {
        val list = ADatabase.INSTANCE!!.getDAO().getAll()
        if(list.size > data.size)
            for (i in data.size until list.size)
                ADatabase.INSTANCE!!.getDAO().delete(list[i])
    }
}


fun deleteExtraClassRowsFromDB(data: List<ClassModel>) = runBlocking {
    withContext(Dispatchers.Default) {
        val list = ADatabase.INSTANCE!!.getClassDAO().getAll()
        if(list.size > data.size)
            for (i in data.size until list.size)
                ADatabase.INSTANCE!!.getClassDAO().delete(list[i])
    }
}