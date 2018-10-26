package ru.uspensky.revolut.di

import android.app.Application
import ru.uspensky.revolut.di.component.DaggerAppComponent
import ru.uspensky.revolut.di.module.AppModule

/**
 * Created by Dmitry Uspensky on 22/10/2018.
 */
object DaggerWrapper {
    private var componentManager: ComponentManager? = null

    fun getComponentManager(application: Application? = null): ComponentManager? {
        if (componentManager == null) {
            val applicationComponent = DaggerAppComponent.builder()
                    .appModule(AppModule(application as Application))
                    .build()
            componentManager = ComponentManager(applicationComponent)
        }

        return componentManager
    }
}