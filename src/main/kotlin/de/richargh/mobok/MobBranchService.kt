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
                steps.add(step)
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

            step(10, "Pull") { git.fetch(prune = true) }
            step(20, "Pull") { git.pull(ffonly = true) }

            when {
                hasLocalMobbingBranch() && hasOriginMobbingBranch()   -> {
                    info(30, "Rejoining mob session")
                    if (!isMobbingBranchActive()) {
                        step(40, "Delete local $wipBranch") { git.delteBranch(wipBranch) }
                        step(50, "Switch to $wipBranch") { git.switchToBranch(wipBranch) }
                    }
                }
                !hasLocalMobbingBranch() && !hasOriginMobbingBranch() -> {
                    info(30, "Creating wip branch")
                    step(40, "Switch to $baseBranch") { git.switchToBranch(baseBranch) }
                    step(50, "Pull") { git.pull(rebase = true) }
                    step(60, "Create $wipBranch") { git.createBranch(wipBranch) }
                    step(70, "Switch to $wipBranch") { git.switchToBranch(wipBranch) }
                    step(80, "Push $wipBranch") { git.pushBranch(wipBranch, remoteName) }
                }
                !hasLocalMobbingBranch() && hasOriginMobbingBranch()  -> {
                    info(30, "Joining mob session")
                    step(40, "Checkout $wipBranch") { git.checkoutBranch(wipBranch) }
                    step(50, "Switch to $wipBranch") { git.switchToBranch(wipBranch) }
                }
                else                                                  -> {
                    info(20, "Purging local branch and start new $wipBranch branch from $baseBranch")
                    step(40, "Delete $wipBranch") { git.delteBranch(wipBranch) }

                    step(50, "Checkout $baseBranch") { git.checkoutBranch(baseBranch) }
                    step(60, "Pull") { git.pull(rebase = true) }

                    step(70, "Create $wipBranch") { git.createBranch(wipBranch) }
                    step(80, "Switch to $wipBranch") { git.switchToBranch(wipBranch) }
                    step(80, "Push $wipBranch") { git.pushBranch(wipBranch, remoteName) }
                }
            }
        }
    }

    fun next() {
        runAsyncSteps {
            info(0, "Next")
            if (!isMobbingBranchActive())
                fail(100, "Git Status", "Not on mob branch")

            if(hasNothingToCommit())
                info(100, "Nothing to commit")
            else {
            }
        }
    }

    private fun isMobbingBranchActive(): Boolean = git.localBranches().any { it.contains("* $wipBranch") }

    private fun hasNothingToCommit(): Boolean = git.status().uncommitedFiles.isEmpty()

    private fun hasLocalMobbingBranch(): Boolean = git.localBranches().any { it.contains(wipBranch) }

    private fun hasOriginMobbingBranch(): Boolean = git.upstreamBranches().any { it.contains(wipBranch) }
}