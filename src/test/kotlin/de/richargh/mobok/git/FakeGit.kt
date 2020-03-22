package de.richargh.mobok.git

class FakeGit(
        private val version: GitVersion = GitVersion.ofRaw("2.fake"),
        private val localBranches: MutableList<String> = mutableListOf("master"),
        private val upstreamBranches: MutableList<String> = mutableListOf("origin/master"),
        private val uncommitedFiles: MutableList<String> = mutableListOf()): Git {

    private val currentBranch = localBranches.first()

    override fun status(): GitStatus = GitStatus.ofRaw(currentBranch, uncommitedFiles)

    override fun version(): GitVersion = version

    override fun localBranches() = localBranches

    override fun upstreamBranches(): List<String> = upstreamBranches

    override fun checkoutBranch(name: String) {
        TODO("Not yet implemented")
    }

    override fun switchToBranch(name: String) {
        TODO("Not yet implemented")
    }

    override fun createBranch(name: String) {
        TODO("Not yet implemented")
    }

    override fun delteBranch(name: String) {
        TODO("Not yet implemented")
    }

    override fun fetch(prune: Boolean) {
        TODO("Not yet implemented")
    }

    override fun pull(rebase: Boolean, ffonly: Boolean) {
        TODO("Not yet implemented")
    }

    override fun pushBranch(name: String, upstream: String) {
        TODO("Not yet implemented")
    }
}
