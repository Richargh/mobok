package de.richargh.mobok

import tornadofx.ViewModel
import tornadofx.asObservable

class MemberVM: ViewModel() {

    val members = mutableListOf(
            Member("Robert"))
            .asObservable()

    fun add(member: Member){
        members.add(member)
    }
}