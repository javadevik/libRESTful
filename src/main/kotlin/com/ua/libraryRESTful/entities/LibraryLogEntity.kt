package com.ua.libraryRESTful.entities

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "logs")
class LibraryLogEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var dateTaking: Timestamp,
    var dateShouldReturn: Timestamp,
    var dateReturning: Timestamp? = null,

    @ManyToOne
    var book: BookEntity,

    @ManyToOne
    var customer: CustomerEntity
)