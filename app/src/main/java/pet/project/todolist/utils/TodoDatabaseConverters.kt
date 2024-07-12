package pet.project.todolist.utils

import androidx.room.TypeConverter
import java.util.stream.Collectors

class TodoDatabaseConverters {
    @TypeConverter
    fun fromStringList(list: List<String>?): String {
        return list?.stream()?.collect(Collectors.joining(",")) ?: ""
    }

    @TypeConverter
    fun stringListFromString(value: String): List<String> {
        return value.split(",")
    }
}