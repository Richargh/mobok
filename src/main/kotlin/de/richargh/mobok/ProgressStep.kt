package de.richargh.mobok

class ProgressStep(val name: String, val status: StepStatus)

sealed class StepStatus {
    object OK: StepStatus()
    data class NOPE(val message: String): StepStatus()
}