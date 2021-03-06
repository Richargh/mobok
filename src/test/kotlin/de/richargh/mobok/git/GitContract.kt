package de.richargh.mobok.git

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled("Only work locally")
abstract class GitContract(val sut: Git) {
    @Test
    fun `current branch should be on master`() {
        // GIVEN

        // WHEN
        val result = sut.status()

        // THEN
        assertThat(result.branch).isEqualTo("master")
    }

    @Test
    fun `current version should be higher than 2`() {
        // GIVEN

        // WHEN
        val result = sut.version()

        // THEN
        assertThat(result).isGreaterThan(GitVersion.ofRaw("2."))
    }

    @Test
    fun `should have at least one remote branch`() {
        // GIVEN

        // WHEN
        val result = sut.upstreamBranches()

        // THEN
        assertThat(result).contains("origin/master")
    }
}