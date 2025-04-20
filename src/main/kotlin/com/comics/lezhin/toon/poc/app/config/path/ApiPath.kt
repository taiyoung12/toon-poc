package com.comics.lezhin.toon.poc.app.config.path

private const val V1 = "/api/v1"

object ApiPath {
    object Ping {
        const val PING: String = "$V1/ping"
    }

    object Auth {
        const val AUTH_ALL: String = "$V1/auth/**"
    }

    object Toon {
        const val TOON_ALL: String = "$V1/toon/**"
    }
}
