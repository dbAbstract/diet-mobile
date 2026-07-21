package dev.yaseyo.coroutines.testing

import dev.yaseyo.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher

class TestDispatcherProvider(
    dispatcher: CoroutineDispatcher = StandardTestDispatcher(),
) : DispatcherProvider {
    override val io = dispatcher
    override val main = dispatcher
    override val default = dispatcher
    override val immediate = dispatcher
}
