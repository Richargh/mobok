package de.richargh.mobok.git

interface Git {

    fun status(): GitStatus

    fun version(): GitVersion

    fun localBranches(): List<String>

    fun upstreamBranches(): List<String>

    fun checkoutBranch(name: String): CliCode

    fun switchToBranch(name: String): CliCode

    fun createBranch(name: String): CliCode

    fun delteBranch(name: String): CliCode

    fun fetch(prune: Boolean): CliCode

    fun pull(rebase: Boolean = true, ffonly: Boolean = false): CliCode

    fun pushBranch(name: String, upstream: String): CliCode
}