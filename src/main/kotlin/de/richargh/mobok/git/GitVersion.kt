package de.richargh.mobok.git

@Suppress("DataClassPrivateConstructor")
data class GitVersion private constructor(val rawValue: String): Comparable<GitVersion> {
    override fun compareTo(other: GitVersion): Int = rawValue.compareTo(other.rawValue)

    companion object {
        fun ofCli(cliResult: List<String>) = GitVersion(cliResult[0].version())
        fun ofRaw(rawValue: String) = GitVersion(rawValue)

        private val GIT_VERSION = """^(git )(version )(?<version>[0-9.]+)$""".toRegex()
        private fun String.version() = GIT_VERSION.matchEntire(this)?.groups?.get("version")?.value ?: "Unknown"
    }
}