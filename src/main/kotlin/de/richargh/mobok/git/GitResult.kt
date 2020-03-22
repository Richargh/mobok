package de.richargh.mobok.git

data class GitResult(val code: CliCode, val message: List<String>)

sealed class CliCode {
    object UNKNOWN_OS: CliCode()
    object EXCEPTION: CliCode()

    object OK: CliCode()
    data class OTHER(val rawValue: Int): CliCode()

    companion object {
        fun ofExitCode(exitCode: Int) = when(exitCode){
            0 -> OK
            else -> OTHER(exitCode)
        }
    }
}