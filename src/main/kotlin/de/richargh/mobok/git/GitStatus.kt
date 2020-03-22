package de.richargh.mobok.git

import de.richargh.mobok.subListOrEmpty

@Suppress("DataClassPrivateConstructor")
data class GitStatus private constructor(val branch: String, val uncommitedFiles: List<String>){
    companion object {
        fun ofCli(cliResult: List<String>) = GitStatus(
                if(cliResult.size > 0)cliResult[0].branch() else "Unknown",
                cliResult.subListOrEmpty(1).map { it.file() })
        fun ofRaw(branch: String, uncommitedFiles: List<String>) = GitStatus(branch, uncommitedFiles)

        // supposed to match: ## master...origin/master [ahead 3]
        private val BRANCH = """(## )(?<branch>[a-zA-Z]+)([.]{3})(?<origin>[a-zA-Z/]+).*""".toRegex()
        private fun String.branch() = BRANCH.matchEntire(this)?.groups?.get("branch")?.value ?: "Unknown"

        private val FILE = """^([ ]*[A-Z?]+)( )(?<file>[\w/\\\.]+)$""".toRegex()
        private fun String.file() = FILE.matchEntire(this)?.groups?.get("file")?.value ?: "Unknown"

    }
}