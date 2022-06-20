package com.ua.libraryRESTful.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "customers")
class CustomerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,
    var firstName: String,
    var lastName: String,
    var birthDate: Timestamp? = null,
    var address: String,
    var workPlace: String,
    var position: String,
    var phone: String,
    var passport: String,

    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "customer")
    var logs: List<LibraryLogEntity> = mutableListOf()
)