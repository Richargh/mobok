package de.richargh.mobok

import tornadofx.asObservable

class PersonsViewModel {

    val items = mutableListOf<PersonViewModel>().asObservable()

    fun add(person: PersonViewModel){
        items.add(person)
    }

}