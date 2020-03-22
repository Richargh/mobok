package de.richargh.mobok.git

interface Git {

    fun status(): GitStatus

    fun version(): GitVersion

    fun localBranches(): List<String>

    fun upstreamBranches(): List<String>

    fun checkoutBranch(name: String)

    fun switchToBranch(name: String)

    fun createBranch(name: String)

    fun delteBranch(name: String)

    fun fetch(prune: Boolean)

    fun pull(rebase: Boolean = true, ffonly: Boolean = false)

    fun pushBranch(name: String, upstream: String)
}