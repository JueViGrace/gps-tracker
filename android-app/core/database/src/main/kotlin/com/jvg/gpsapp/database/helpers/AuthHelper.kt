package com.jvg.gpsapp.database.helpers

import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import com.jvg.gpsapp.database.driver.DriverFactory
import com.jvg.gpsapp.util.Logs
import com.jvg.gpsapp.util.coroutines.CoroutineProvider
import migrations.Gps_session

interface AuthHelper : DbHelper {
    suspend fun getSession(): Gps_session?
    suspend fun createSession(session: Gps_session)
    suspend fun updateSession(session: Gps_session)
    suspend fun deleteSession()
}

class DefaultAuthHelper(
    override val driver: DriverFactory,
    override val coroutineProvider: CoroutineProvider
) : AuthHelper {
    override suspend fun getSession(): Gps_session? {
        return withDatabase { db ->
            executeOne(db.sessionQueries.findSession())
        }
    }

    override suspend fun createSession(session: Gps_session) {
        withDatabase { db ->
            db.transaction {
                db.sessionQueries.insertSession(session)
            }
        }
    }

    override suspend fun updateSession(session: Gps_session) {
        Logs.debug(tag = tag, msg = "Updating session: $session")
        withDatabase { db ->
            db.transaction {
                db.sessionQueries.updateToken(
                    access_token = session.access_token,
                    refresh_token = session.refresh_token,
                    id = session.id
                )
                db.sessionQueries.updateActive(
                    active = session.active,
                    id = session.id
                )
            }
        }
    }

    override suspend fun deleteSession() {
        withDatabase { db ->
            db.transaction {
                db.sessionQueries.deleteSession()
            }
        }
    }
}
