package com.ua.libraryRESTful.factory.impl

import com.ua.libraryRESTful.factory.MapFactory
import org.springframework.stereotype.Component

@Component
class MapCustomerFactory : MapFactory {
    override fun create(): Map<String, String> {
        val map = HashMap<String, String>()
        map["id"] = "1"
        map["firstName"] = "Bob"
        map["lastName"] = "Lutter"
        map["address"] = "USA, Will"
        map["workPlace"] = "Google"
        map["position"] = "Developer"
        map["phone"] = "+10355572123"
        map["passport"] = "dfgkj2321j"
        return map
    }

    override fun createUpdated(): Map<String, String> {
        val map = HashMap<String, String>()
        map["firstName"] = "Bob"
        map["lastName"] = "Lutter"
        map["address"] = "USA, Will"
        map["workPlace"] = "Google"
        map["position"] = "Developer"
        map["phone"] = "+10355572123"
        map["passport"] = "xgj1231jvdj"
        return map
    }
}