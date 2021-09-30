package com.rolandoselvera.founditall.core.base

import android.app.Application
import com.rolandoselvera.founditall.data.local.db.ResultRoomDatabase

class BaseApplication : Application() {
    // Se utiliza el delegado 'lazy' para que crear la instancia database de forma diferida
    // cuando se consulte la referencia por 1a. vez (en lugar de hacerlo cuando se
    // inicie la app). Esta acción creará la base de datos (la base de datos física en el
    // dispositivo) en el primer acceso.
    val database: ResultRoomDatabase by lazy { ResultRoomDatabase.getDatabase(this) }
}