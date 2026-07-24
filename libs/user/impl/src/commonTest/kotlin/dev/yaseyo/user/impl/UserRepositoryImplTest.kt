package dev.yaseyo.user.impl

import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import dev.yaseyo.user.impl.network.FakeUserApi
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class UserRepositoryImplTest {
    private fun repo(
        fakeApi: FakeUserApi = FakeUserApi(),
        dataStore: FakePreferencesDataStore = FakePreferencesDataStore(),
    ) = UserRepositoryImpl(fakeApi, dataStore)

    // --- getCurrentUser ---

    @Test
    fun `getCurrentUser returns success with mapped domain object`() {
        runBlocking {
            val result = repo().getCurrentUser()
            assertTrue(result.isSuccess)
            val user = result.getOrThrow()
            assertEquals("usr_abc123", user.id)
            assertEquals("Alex Smith", user.name)
            assertIs<Sex>(user.sex)
            assertEquals(Sex.Male, user.sex)
            assertEquals(ActivityLevel.LightlyActive, user.activityLevel)
            assertEquals(LocalDate(1990, 6, 15), user.dateOfBirth)
            assertIs<kotlin.time.Instant>(user.createdAt)
            assertIs<kotlin.time.Instant>(user.updatedAt)
        }
    }

    @Test
    fun `getCurrentUser returns failure when api throws`() =
        runBlocking {
            val error = RuntimeException("network error")
            val result = repo(FakeUserApi(error = error)).getCurrentUser()
            assertTrue(result.isFailure)
            assertEquals(error, result.exceptionOrNull())
        }

    // --- createUser ---

    @Test
    fun `createUser returns success with mapped domain object`() =
        runBlocking {
            val result = repo().createUser(
                name = "Alex Smith",
                sex = Sex.Male,
                height = 178.0,
                dateOfBirth = LocalDate(1990, 6, 15),
                activityLevel = ActivityLevel.LightlyActive,
                targetWeightKg = 80.0,
                dailyDeficitKcal = 500.0,
                targetProtein = 160.0,
                targetCarbs = 200.0,
                targetFat = 70.0,
            )
            assertTrue(result.isSuccess)
            assertEquals("usr_abc123", result.getOrThrow().id)
        }

    @Test
    fun `createUser returns failure when api throws`() =
        runBlocking {
            val error = RuntimeException("network error")
            val result = repo(FakeUserApi(error = error)).createUser(
                name = "Alex Smith",
                sex = Sex.Male,
                height = 178.0,
                dateOfBirth = LocalDate(1990, 6, 15),
                activityLevel = ActivityLevel.LightlyActive,
                targetWeightKg = 80.0,
                dailyDeficitKcal = 500.0,
                targetProtein = 160.0,
                targetCarbs = 200.0,
                targetFat = 70.0,
            )
            assertTrue(result.isFailure)
            assertEquals(error, result.exceptionOrNull())
        }

    // --- updateUser ---

    @Test
    fun `updateUser returns success with mapped domain object`() =
        runBlocking {
            val result = repo().updateUser(name = "New Name")
            assertTrue(result.isSuccess)
            assertEquals("usr_abc123", result.getOrThrow().id)
        }

    @Test
    fun `updateUser returns failure when api throws`() =
        runBlocking {
            val error = RuntimeException("network error")
            val result = repo(FakeUserApi(error = error)).updateUser(name = "New Name")
            assertTrue(result.isFailure)
            assertEquals(error, result.exceptionOrNull())
        }

    // --- isUserSessionActive ---

    @Test
    fun `isUserSessionActive returns failure when nothing cached`() =
        runBlocking {
            val result = repo().isUserSessionActive()
            assertTrue(result.isFailure)
        }

    @Test
    fun `isUserSessionActive returns success after getCurrentUser caches session`() =
        runBlocking {
            val dataStore = FakePreferencesDataStore()
            val repo = repo(dataStore = dataStore)
            repo.getCurrentUser()

            val result = repo.isUserSessionActive()
            assertTrue(result.isSuccess)
        }

    @Test
    fun `isUserSessionActive returns failure after clearUserSession`() =
        runBlocking {
            val dataStore = FakePreferencesDataStore()
            val repo = repo(dataStore = dataStore)
            repo.getCurrentUser()
            repo.clearUserSession()

            val result = repo.isUserSessionActive()
            assertTrue(result.isFailure)
        }

    // --- clearUserSession ---

    @Test
    fun `clearUserSession returns success`() =
        runBlocking {
            val result = repo().clearUserSession()
            assertTrue(result.isSuccess)
        }
}
