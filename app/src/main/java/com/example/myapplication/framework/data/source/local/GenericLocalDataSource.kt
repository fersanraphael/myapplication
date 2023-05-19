package com.example.myapplication.framework.data.source.local

import com.example.myapplication.domain.util.Result
import com.example.myapplication.framework.MyApplicationRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.TRUE_PREDICATE
import io.realm.kotlin.types.RealmObject

/**
 * @author Raphael Fersan
 */
internal class GenericLocalDataSource constructor(
    private val myApplicationRealm: MyApplicationRealm
) {

    private val realm: Realm? by lazy {
        myApplicationRealm.get()
    }

    suspend fun <R : RealmObject> add(data: R): Result<R> {
        return try {
            val realm: Realm = realm ?: return Result.Failure()
            val response: R = realm.write {
                copyToRealm(data)
            }
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    internal inline fun <reified R : RealmObject> get(query: String = TRUE_PREDICATE): Result<List<R>> {
        return try {
            val realm: Realm = realm ?: return Result.Failure()
            val response: List<R> = realm.run {
                copyFromRealm(query<R>(query).find())
            }
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}
