package com.ua.libraryRESTful.factory

interface MapFactory {
    fun create(): Map<String, String>
    fun createUpdated(): Map<String, String>
}