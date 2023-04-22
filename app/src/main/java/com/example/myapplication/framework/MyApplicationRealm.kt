package com.example.myapplication.framework

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

/**
 * @author Raphael Fersan
 */
internal class MyApplicationRealm constructor(
    private val realmConfiguration: RealmConfiguration
) {

    init {
        try {
            open()
        } catch (e: Exception) {
            print(e)
        }
    }

    private var realm: Realm? = null

    @Throws(
        IllegalArgumentException::class,
        IllegalStateException::class,
        NullPointerException::class
    )
    private fun open(): Realm? {
        realm = Realm.open(realmConfiguration)
        return realm
    }

    fun get(): Realm? {
        return realm ?: try {
            open()
        } catch (e: Exception) {
            null
        }
    }

    fun close() {
        if (realm?.isClosed() == false) {
            realm?.close()
        }
        realm = null
    }
}
