package ru.uspensky.revolut.di.component

import dagger.Component
import ru.uspensky.revolut.di.ModuleScope
import ru.uspensky.revolut.di.module.RatesModule
import ru.uspensky.revolut.presentation.view.RatesActivity

/**
 * Created by Dmitry Uspensky on 22/10/2018.
 */
@ModuleScope
@Component(dependencies = [AppComponent::class], modules = [RatesModule::class])
interface RatesComponent {
    fun inject(controller: RatesActivity)
}