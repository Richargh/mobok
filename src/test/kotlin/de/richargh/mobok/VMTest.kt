package de.richargh.mobok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class VMTest {

    @Test
    fun `foo`(){
        // given
        val sut = MemberVM()

        // when
        val result = sut.members

        // then
        assertThat(result).hasSize(1)
    }

}