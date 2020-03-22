package de.richargh.mobok.git

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import kotlin.streams.toList

class FileSystemGit(val baseFolder: File = File(".")): Git {

    override fun status(): GitStatus {
        // TODO what do I do about this (error) code?
        val (code, result) = git("status", "--short", "--branch")

        return GitStatus.ofCli(result)
    }

    override fun version(): GitVersion {
        // TODO what do I do about this (error) code?
        val (code, result) = git("version")
        return GitVersion.ofCli(result)
    }

    private fun git(vararg arguments: String): GitResult {
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
            val lines = reader.lines().toList()
            val exitCode = process.waitFor()
            reader.close()

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