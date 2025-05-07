package com.jvg.gpsapp.app.data

import com.jvg.gpsapp.shared.data.Repository
import com.jvg.gpsapp.util.coroutines.CoroutineProvider

interface AppRepository : Repository

class DefaultAppRepository(override val coroutineProvider: CoroutineProvider) : AppRepository
