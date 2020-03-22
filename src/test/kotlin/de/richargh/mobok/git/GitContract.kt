package de.richargh.mobok.git

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

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
}