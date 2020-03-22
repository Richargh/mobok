package de.richargh.mobok

import de.richargh.mobok.git.CliCode
import de.richargh.mobok.git.Git
import org.reactfx.EventSource
import tornadofx.Component

class MobBranchService(val git: Git): Component() {

    private val events: Events by inject()

    private val wipBranch = "mob-session"
    private val baseBranch = "master"
    private val remoteName = "origin"
    private val wipCommitMessage = "mob next [ci-skip]"

    class StepBuilder(private val globalProgress: EventSource<GlobalProgress>) {
        private val steps = mutableListOf<ProgressStep>()
        private var hasFailed = false

        fun info(progress: Int, name: String) {
            if (!hasFailed) {
                steps.add(ProgressStep(name, StepStatus.OK))
                globalProgress.pushLater(GlobalProgress(progress, steps))
            }
        }

        fun fail(progress: Int, name: String, message: String) {
            if (!hasFailed) {
                steps.add(ProgressStep(name, StepStatus.NOPE(message)))
                globalProgress.pushLater(GlobalProgress(progress, steps))
                hasFailed = true
            }
        }

        fun step(progress: Int, name: String, block: () -> CliCode) {
            if (!hasFailed) {
                val result = block()
                val step = if (result is CliCode.KO) {
                    hasFailed = true
                    ProgressStep(name, StepStatus.NOPE(result.message))
                } else {
                    ProgressStep(name, StepStatus.OK)
                }
                globalProgress.pushLater(GlobalProgress(progress, steps))
            }
        }
    }

    fun runAsyncSteps(block: StepBuilder.() -> Unit) {
        runAsync {
            val stepBuilder = StepBuilder(events.globalProgress)
            stepBuilder.block()

        }
    }

    fun start() {
        runAsyncSteps {
            info(0, "Start")
            if (git.status().uncommitedFiles.isNotEmpty())
                fail(100, "Git Status", "Uncommited Files")

            step(10, "Pull") {
                git.fetch(prune = true)
                git.pull(ffonly = true)
            }

            when {
                hasLocalMobbingBranch() && hasOriginMobbingBranch()   -> {
                    info(20, "Rejoining mob session")
                    if (!isMobbingBranchActive()) {
                        step(30, "Delete local $wipBranch") { git.delteBranch(wipBranch) }
                        step(40, "Switch to remote $wipBranch") { git.switchToBranch(wipBranch) }
                    }
                }
                !hasLocalMobbingBranch() && !hasOriginMobbingBranch() -> {
                    info(20, "Creating wip branch")
                    git.switchToBranch(baseBranch)
                    git.pull(rebase = true)
                    git.createBranch(wipBranch)
                    git.switchToBranch(wipBranch)
                    git.pushBranch(wipBranch, remoteName)
                }
                !hasLocalMobbingBranch() && hasOriginMobbingBranch()  -> {
                    info(20, "Joining mob session")
                    git.checkoutBranch(wipBranch)
                    git.switchToBranch(wipBranch)
                }
                else                                                  -> {
                    info(20, "purging local branch and start new $wipBranch branch from $baseBranch")
                    //                deleteLocalMobbingBranch()
                }
            }
        }
    }

    private fun checkoutMobbingBranch() = git.switchToBranch(wipBranch)

    private fun deleteLocalMobbingBranch() = git.delteBranch(wipBranch)

    private fun isMobbingBranchActive(): Boolean = git.localBranches().any { it.contains("* $wipBranch") }

    private fun hasLocalMobbingBranch(): Boolean = git.localBranches().any { it.contains(wipBranch) }

    private fun hasOriginMobbingBranch(): Boolean = git.upstreamBranches().any { it.contains(wipBranch) }
}