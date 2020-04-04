package de.richargh.mobok.git

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled("Only work locally")
class GitStatusTest {

    @Test
    fun `should find master branch when on master`() {
        // GIVEN

        // WHEN
        val result = GitStatus.ofCli(
                listOf("## master...origin/master"))

        // THEN
        assertThat(result.branch).isEqualTo("master")
    }

    @Test
    fun `should find master branch when ahead on master`() {
        // GIVEN

        // WHEN
        val result = GitStatus.ofCli(
                listOf("## master...origin/master [ahead 3]"))

        // THEN
        assertThat(result.branch).isEqualTo("master")
    }

    @Test
    fun `should find master branch when ahead and behind on master`() {
        // GIVEN

        // WHEN
        val result = GitStatus.ofCli(
                listOf("## master...origin/master [ahead 1, behind 5]"))

        // THEN
        assertThat(result.branch).isEqualTo("master")
    }

    @Test
    fun `should find no uncommitted files when none are supplied`() {
        // GIVEN

        // WHEN
        val result = GitStatus.ofCli(
                listOf("## master...origin/master [ahead 3]"))

        // THEN
        assertThat(result.uncommitedFiles).isEmpty()
    }

    @Test
    fun `should find one modified file when one is supplied`() {
        // GIVEN

        // WHEN
        val result = GitStatus.ofCli(
                listOf("## master...origin/master [ahead 3]",
                       " M src/main/kotlin/de/richargh/mobok/MobControlVM.kt"))

        // THEN
        assertThat(result.uncommitedFiles).containsExactly("src/main/kotlin/de/richargh/mobok/MobControlVM.kt")
    }

    @Test
    fun `should find one added file when one is supplied`() {
        // GIVEN

        // WHEN
        val result = GitStatus.ofCli(
                listOf("## master...origin/master [ahead 3]",
                       "AM src/main/kotlin/de/richargh/mobok/git/Git.kt"))

        // THEN
        assertThat(result.uncommitedFiles).containsExactly("src/main/kotlin/de/richargh/mobok/git/Git.kt")
    }

    @Test
    fun `should find one untracked file when one is supplied`() {
        // GIVEN

        // WHEN
        val result = GitStatus.ofCli(
                listOf("## master...origin/master [ahead 3]",
                       "?? mobok.config"))

        // THEN
        assertThat(result.uncommitedFiles).containsExactly("mobok.config")
    }

    @Test
    fun `should find one deleted file when one is supplied`() {
        // GIVEN

        // WHEN
        val result = GitStatus.ofCli(
                listOf("## master...origin/master [ahead 3]",
                       "D  mobok.config"))

        // THEN
        assertThat(result.uncommitedFiles).containsExactly("mobok.config")
    }
}