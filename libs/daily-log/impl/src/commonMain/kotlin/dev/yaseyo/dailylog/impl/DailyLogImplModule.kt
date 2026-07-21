package dev.yaseyo.dailylog.impl

import dev.yaseyo.dailylog.api.DailyLogRepository
import dev.yaseyo.dailylog.impl.network.DailyLogApi
import dev.yaseyo.dailylog.impl.network.DailyLogApiImpl
import dev.yaseyo.dailylog.impl.repository.DailyLogRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dailyLogImplModule = module {
    singleOf(::DailyLogRepositoryImpl) bind DailyLogRepository::class
    singleOf(::DailyLogApiImpl) bind DailyLogApi::class
}
