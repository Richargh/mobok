package de.richargh.mobok.git

interface Git {

    fun status(): GitStatus

    fun version(): GitVersion

}