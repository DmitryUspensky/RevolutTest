package ru.uspensky.revolut.di

import ru.uspensky.revolut.di.component.AppComponent
import ru.uspensky.revolut.di.component.DaggerRatesComponent
import ru.uspensky.revolut.di.component.RatesComponent
import ru.uspensky.revolut.di.module.RatesModule

/**
 * Created by Dmitry Uspensky on 22/10/2018.
 */
open class ComponentManager(val appComponent: AppComponent) {

    private var ratesComponent: RatesComponent? = null

    open fun getRatesComponent(): RatesComponent? {
        if (ratesComponent == null) {
            ratesComponent = DaggerRatesComponent.builder()
                    .appComponent(appComponent)
                    .ratesModule(RatesModule())
                    .build()
        }
        return ratesComponent
    }

    open fun clearRatesComponent() {
        ratesComponent = null
    }
}