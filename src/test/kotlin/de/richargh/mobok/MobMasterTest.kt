package de.richargh.mobok

import de.richargh.mobok.git.FakeGit
import de.richargh.mobok.git.GitVersion
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MobMasterTest {

    @Test
    fun `current version should be higher than 2`() {
        // GIVEN
        val git = FakeGit()
        val sut = MobMaster(git)

        // WHEN
        sut.start()

        // THEN
        assertThat(git.status().branch).isEqualTo("master")
    }
}