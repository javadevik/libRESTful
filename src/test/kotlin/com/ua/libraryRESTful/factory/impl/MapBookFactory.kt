package com.ua.libraryRESTful.factory.impl

import com.ua.libraryRESTful.factory.MapFactory
import org.springframework.stereotype.Component

@Component
class MapBookFactory : MapFactory {
    override fun create(): Map<String, String> {
        val map = HashMap<String, String>()
        map["id"] = "1"
        map["title"] = "Title"
        map["author"] = "Author"
        map["publisher"] = "Publisher"
        map["year"] = "1970"
        map["genre"] = "Genre"
        map["numberOfPage"] = "348"
        return map
    }

    override fun createUpdated(): Map<String, String> {
        val map = HashMap<String, String>()
        map["title"] = "Title"
        map["author"] = "Author"
        map["publisher"] = "Publisher"
        map["year"] = "1970"
        map["genre"] = "Genre"
        map["numberOfPage"] = "348"
        return map
    }

}