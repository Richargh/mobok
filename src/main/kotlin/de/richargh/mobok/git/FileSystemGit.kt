package de.richargh.mobok.git

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

class FileSystemGit(val baseFolder: File = File(".")) {

    fun git(vararg arguments: String): GitResult {
        val os = System.getProperty("os.name")
        val processBuilder = when {
            os.toLowerCase().contains("mac") ->
                ProcessBuilder("bash", "-c", "git ${arguments.joinToString(" ")}")
            os.toLowerCase().contains("win") ->
                ProcessBuilder("cmd.exe", "/c", "git ${arguments.joinToString(" ")}")
            else                             ->
                return GitResult(CliCode.UNKNOWN_OS, emptyList())
        }
        processBuilder.directory(baseFolder)

        return try {
            val process = processBuilder.start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val lines: List<String> = reader.useLines { it }.toList()
            val exitCode = process.waitFor()
            GitResult(CliCode.ofExitCode(exitCode), lines)
        } catch (e: IOException) {
            e.printStackTrace()
            GitResult(CliCode.EXCEPTION, listOf(e.toString()))
        } catch (e: InterruptedException) {
            e.printStackTrace()
            GitResult(CliCode.EXCEPTION, listOf(e.toString()))
        }
    }
}