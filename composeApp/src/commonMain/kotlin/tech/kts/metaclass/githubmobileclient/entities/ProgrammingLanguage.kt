package tech.kts.metaclass.githubmobileclient.entities

/**
 * Only the most popular languages are presented, [OTHER] value used for others.
 *
 * Colors are taken from https://github.com/github-linguist/linguist
 */
enum class ProgrammingLanguage(
    val title: String,
    val color: Long
) {
    PYTHON("Python", 0xFF3572A5),
    C("C", 0xFF555555),
    CPP("C++", 0xFFF34B7D),
    JAVA("Java", 0xFFB07219),
    C_SHARP("C#", 0xFF178600),
    JAVASCRIPT("JavaScript", 0xFFF1E05A),
    GO("Go", 0xFF00ADD8),
    SQL("SQL", 0xFFE38C00),
    R("R", 0xFF198CE7),
    PERL("Perl",0xFF0298C3),
    FORTRAN("FORTRAN",0xFF4D41B1),
    RUST("Rust", 0xFFDEA584),
    MATLAB("Matlab",0xFFE16737),
    PHP("PHP",0xFF4F5D95),
    SWIFT("Swift",0xFFF05138),
    KOTLIN("Kotlin",0xFFA97BFF),
    DART("Dart",0xFF00B4AB),
    LUA("Lua", 0xFF000080),
    POWERSHELL("PowerShell", 0xFF012456),
    TYPESCRIPT("TypeScript", 0xFF3178C6),
    SCALA("Scala",0xFFC22D40),
    RUBY("Ruby",0xFF701516),
    OBJECTIVE_C("Objective-C",0xFF438EFF),
    HTML("HTML",0xFFE34C26),
    CSS("CSS",0xFF663399),
    OTHER("Other", 0xFF808080),
}