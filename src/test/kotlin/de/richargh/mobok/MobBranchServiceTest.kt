package de.richargh.mobok

import de.richargh.mobok.git.FakeGit
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class MobBranchServiceTest {

    @Disabled("FakeGit not yet ready")
    @Test
    fun `current version should be higher than 2`() {
        // GIVEN
        val git = FakeGit()
        val sut = MobBranchService(git)

        // WHEN
        sut.start()

        // THEN
        assertThat(git.status().branch).isEqualTo("master")
    }
}