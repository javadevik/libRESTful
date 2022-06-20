package com.ua.libraryRESTful.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "books")
class BookEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,
    var title: String,
    var author: String,
    var publisher: String,
    var year: Int,
    var genre: String,
    var numberOfPage: Int,

    var isBusy: Boolean = false,
    var isExcluded: Boolean = false,
    var reasonExclude: String? = null,

    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "book")
    var logs: List<LibraryLogEntity> = mutableListOf()
)