package tech.kts.metaclass.githubmobileclient.data.network.mappers

import tech.kts.metaclass.githubmobileclient.entities.ProgrammingLanguage

class ApiProgrammingLanguageMapper {
    fun toDomainModel(rawLanguage: String?): ProgrammingLanguage {
        return when (rawLanguage) {
            "Python", "Python console", "Python traceback" -> ProgrammingLanguage.PYTHON
            "C" -> ProgrammingLanguage.C
            "C++" -> ProgrammingLanguage.CPP
            "Java" -> ProgrammingLanguage.JAVA
            "C#" -> ProgrammingLanguage.C_SHARP
            "JavaScript", "JavaScript+ERB" -> ProgrammingLanguage.JAVASCRIPT
            "Go" -> ProgrammingLanguage.GO
            "SQL", "PLSQL", "PLpgSQL", "TSQL" -> ProgrammingLanguage.SQL
            "R", "RMarkdown" -> ProgrammingLanguage.R
            "Perl", "Perl 6" -> ProgrammingLanguage.PERL
            "FORTRAN", "Fortran Free Form" -> ProgrammingLanguage.FORTRAN
            "Rust" -> ProgrammingLanguage.RUST
            "Matlab" -> ProgrammingLanguage.MATLAB
            "PHP" -> ProgrammingLanguage.PHP
            "Swift" -> ProgrammingLanguage.SWIFT
            "Kotlin" -> ProgrammingLanguage.KOTLIN
            "Dart" -> ProgrammingLanguage.DART
            "Lua" -> ProgrammingLanguage.LUA
            "PowerShell" -> ProgrammingLanguage.POWERSHELL
            "TypeScript" -> ProgrammingLanguage.TYPESCRIPT
            "Scala" -> ProgrammingLanguage.SCALA
            "Ruby" -> ProgrammingLanguage.RUBY
            "Objective-C", "Objective-C++", "Objective-J" -> ProgrammingLanguage.OBJECTIVE_C
            "HTML" -> ProgrammingLanguage.HTML
            "CSS" -> ProgrammingLanguage.CSS
            else -> ProgrammingLanguage.OTHER
        }
    }
}