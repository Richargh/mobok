package de.richargh.mobok.git

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import kotlin.streams.toList

class FileSystemGit(private val baseFolder: File = File("."), private val upstream: String = "origin"): Git {

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

    override fun localBranches(): List<String> {
        val (code, result) = git("branch")
        return result.map { it.trim() }
    }

    override fun upstreamBranches(): List<String> {
        val (code, result) = git("branch", "--remotes")
        return result.map { it.trim() }
    }

    override fun checkoutBranch(name: String){
        git("checkout", name)
    }

    override fun switchToBranch(name: String){
        git("checkout", name)
    }

    override fun createBranch(name: String) {
        git("checkout", name)
    }

    override fun delteBranch(name: String) {
        git("branch", "-D", name)
    }

    override fun fetch(prune: Boolean) {
        val args = sequence {
            yield("fetch")
            if(prune) yield("--prune")
        }.toList()
        val (code, result) = git(args)
    }

    override fun pull(rebase: Boolean, ffonly: Boolean) {
        val args = sequence {
            yield("pull")
            if(rebase) yield("--rebase")
            if(ffonly) yield("--ff-only")
        }.toList()
        val (code, result) = git(args)
    }

    override fun pushBranch(name: String, upstream: String) {
        git("push", "-u", upstream, name)
    }

    private fun git(vararg arguments: String) = git(arguments.asList())

    private fun git(arguments: List<String>): GitResult {
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