package de.richargh.mobok

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

class GitAdapter(val baseFolder: File = File(".")){
    fun git(vararg arguments: String){
        val os = System.getProperty("os.name")
        val processBuilder = if (os.toLowerCase().contains("mac")) {
            println("We're on Mac => [$os]")
            ProcessBuilder("bash", "-c", "git ${arguments.joinToString(" ")}")
        } else if (os.toLowerCase().contains("win")) {
            println("We're on Windows => [$os]")
            ProcessBuilder("cmd.exe", "/c", "git ${arguments.joinToString(" ")}")
        } else {
            println("OS is unknown => [$os]")
            return
        }
        processBuilder.directory(baseFolder)

        try {
            val process = processBuilder.start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            reader.useLines { lines ->
                println(lines.joinToString("\n"))
            }
            val exitCode = process.waitFor()
            if (exitCode != 0) {
                println("\nSomething went wrong, the exit code was: [$exitCode]")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}