package de.richargh.mobok.git

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class GitVersionTest {
    @Test
    fun `should find master branch when on master`() {
        // GIVEN

        // WHEN
        val result = GitVersion.ofCli(
                listOf("git version 2.25.1"))

        // THEN
        Assertions.assertThat(result).isEqualTo(GitVersion.ofRaw("2.25.1"))
    }
}