package de.richargh.mobok

class GlobalProgress(val progress: Int, val steps: List<ProgressStep>){
    constructor(progress: Int, vararg steps: ProgressStep): this(progress, steps.toList())
}