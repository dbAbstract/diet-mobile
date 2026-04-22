package dev.yaseyo.navigation

import org.koin.androidx.scope.dsl.activityRetainedScope
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module

val navigationAndroidModule = module {
    activityRetainedScope {
        scopedOf(::Navigator)
    }
}
