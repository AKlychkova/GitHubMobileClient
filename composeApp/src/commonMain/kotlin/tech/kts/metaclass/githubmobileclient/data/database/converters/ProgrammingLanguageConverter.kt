package tech.kts.metaclass.githubmobileclient.data.database.converters

import androidx.room.TypeConverter
import tech.kts.metaclass.githubmobileclient.entities.ProgrammingLanguage

class ProgrammingLanguageConverter {
    @TypeConverter
    fun fromLanguage(language: ProgrammingLanguage): String = language.name

    @TypeConverter
    fun toLanguage(value: String): ProgrammingLanguage = ProgrammingLanguage.valueOf(value)
}