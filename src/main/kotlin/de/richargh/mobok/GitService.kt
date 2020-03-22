package de.richargh.mobok

import de.richargh.mobok.git.Git

class GitService(val git: Git) {

    private val wipBranch = "mob-session"
    private val baseBranch = "master"
    private val remoteName = "origin"
    private val wipCommitMessage = "mob next [ci-skip]"

    fun start() {
        val status = git.status()
        println(status)
        if (git.status().uncommitedFiles.isNotEmpty()) {
            println("Aborting: found uncommitted files")
            return
        }
        git.fetch(prune = true)
        git.pull(ffonly = true)

        when {
            hasLocalMobbingBranch() && hasOriginMobbingBranch() -> {
                println("Rejoining mob session")
                if(!isMobbingBranchActive()){
                    git.delteBranch(wipBranch)
                    git.switchToBranch(wipBranch)
                }
            }
            !hasLocalMobbingBranch() && !hasOriginMobbingBranch() -> {
                println("Creating wip branch")
                git.switchToBranch(baseBranch)
                git.pull(rebase = true)
                git.createBranch(wipBranch)
                git.switchToBranch(wipBranch)
                git.pushBranch(wipBranch, remoteName)
            }
            !hasLocalMobbingBranch() && hasOriginMobbingBranch() -> {
                println("joining mob session")
                git.checkoutBranch(wipBranch)
                git.switchToBranch(wipBranch)
            }
            else -> {
                println("purging local branch and start new $wipBranch branch from $baseBranch")
//                deleteLocalMobbingBranch()

            }
        }
    }

    private fun checkoutMobbingBranch()
            = git.switchToBranch(wipBranch)

    private fun deleteLocalMobbingBranch()
        = git.delteBranch(wipBranch)

    private fun isMobbingBranchActive(): Boolean
            = git.localBranches().any { it.contains("* $wipBranch") }

    private fun hasLocalMobbingBranch(): Boolean
        = git.localBranches().any { it.contains(wipBranch) }

    private fun hasOriginMobbingBranch(): Boolean
            = git.upstreamBranches().any { it.contains(wipBranch) }
}