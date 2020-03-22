package de.richargh.mobok.git

data class GitResult(val code: CliCode, val message: List<String>)

sealed class CliCode {
    sealed class KO(open val message: String): CliCode() {
        data class UNKNOWN_OS(val os: String): KO(os)
        data class EXCEPTION(val stacktrace: String): KO(stacktrace)
        data class OTHER(val rawValue: Int): CliCode()
    }


    object OK: CliCode()

    companion object {
        fun ofExitCode(exitCode: Int) = when(exitCode){
            0 -> OK
            else -> KO.OTHER(exitCode)
        }
    }
}