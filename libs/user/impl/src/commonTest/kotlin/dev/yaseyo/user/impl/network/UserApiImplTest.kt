package dev.yaseyo.user.impl.network

import dev.yaseyo.user.api.ActivityLevel
import dev.yaseyo.user.api.Sex
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.OutgoingContent
import io.ktor.http.contentType
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserApiImplTest {
    private lateinit var mockEngine: MockEngine
    private lateinit var api: UserApiImpl

    @BeforeTest
    fun setUp() {
        mockEngine = MockEngine {
            respond(
                content = USER_NET_JSON,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
        api = UserApiImpl(client)
    }

    // --- getUser ---

    @Test
    fun `getUser sends GET to user path`() =
        runBlocking {
            api.getUser()
            val request = mockEngine.requestHistory.last()
            assertEquals(HttpMethod.Get, request.method)
            assertEquals("/user/", request.url.encodedPath)
        }

    @Test
    fun `getUser deserializes all fields`() =
        runBlocking {
            val result = api.getUser()
            assertEquals("usr_abc123", result.id)
            assertEquals("Alex Smith", result.name)
            assertEquals("MALE", result.sex)
            assertEquals(178.0, result.height)
            assertEquals("1990-06-15", result.dateOfBirth)
            assertEquals("LIGHTLY_ACTIVE", result.activityLevel)
            assertEquals(80.0, result.targetWeightKg)
            assertEquals(500.0, result.dailyDeficitKcal)
            assertEquals(160.0, result.targetProtein)
            assertEquals(200.0, result.targetCarbs)
            assertEquals(70.0, result.targetFat)
            assertEquals("2024-01-10T08:00:00Z", result.createdAt)
            assertEquals("2024-03-20T14:30:00Z", result.updatedAt)
        }

    // --- createUser ---

    @Test
    fun `createUser sends POST to user path`() =
        runBlocking {
            api.createUser(
                name = "Alex Smith",
                sex = Sex.Male,
                height = 178.0,
                dateOfBirth = LocalDate(
                    year = 1990,
                    month = 6,
                    day = 15,
                ),
                activityLevel = ActivityLevel.LightlyActive,
                targetWeightKg = 80.0,
                dailyDeficitKcal = 500.0,
                targetProtein = 160.0,
                targetCarbs = 200.0,
                targetFat = 70.0,
            )
            val request = mockEngine.requestHistory.last()
            assertEquals(HttpMethod.Post, request.method)
            assertEquals("/user/", request.url.encodedPath)
        }

    @Test
    fun `createUser sends correct body`() =
        runBlocking {
            api.createUser(
                name = "Alex Smith",
                sex = Sex.Male,
                height = 178.0,
                dateOfBirth = LocalDate(
                    year = 1990,
                    month = 6,
                    day = 15,
                ),
                activityLevel = ActivityLevel.LightlyActive,
                targetWeightKg = 80.0,
                dailyDeficitKcal = 500.0,
                targetProtein = 160.0,
                targetCarbs = 200.0,
                targetFat = 70.0,
            )
            val body = lastRequestBodyJson()
            assertEquals("Alex Smith", body["name"]?.toString()?.trim('"'))
            assertEquals("MALE", body["sex"]?.toString()?.trim('"'))
            assertEquals("LIGHTLY_ACTIVE", body["activityLevel"]?.toString()?.trim('"'))
            assertEquals("1990-06-15", body["dateOfBirth"]?.toString()?.trim('"'))
        }

    @Test
    fun `createUser returns deserialized response`() =
        runBlocking {
            val result = api.createUser(
                name = "Alex Smith",
                sex = Sex.Male,
                height = 178.0,
                dateOfBirth = LocalDate(
                    year = 1990,
                    month = 6,
                    day = 15,
                ),
                activityLevel = ActivityLevel.LightlyActive,
                targetWeightKg = 80.0,
                dailyDeficitKcal = 500.0,
                targetProtein = 160.0,
                targetCarbs = 200.0,
                targetFat = 70.0,
            )
            assertEquals("usr_abc123", result.id)
        }

    // --- updateUser ---

    @Test
    fun `updateUser sends PATCH to user path`() =
        runBlocking {
            api.updateUser(name = "New Name")
            val request = mockEngine.requestHistory.last()
            assertEquals(HttpMethod.Patch, request.method)
            assertEquals("/user/", request.url.encodedPath)
        }

    @Test
    fun `updateUser omits null fields`() =
        runBlocking {
            api.updateUser(name = "New Name", targetWeightKg = 75.0)
            val body = lastRequestBodyJson()
            assertEquals(2, body.size)
            assertTrue(body.containsKey("name"))
            assertTrue(body.containsKey("targetWeightKg"))
            assertFalse(body.containsKey("sex"))
            assertFalse(body.containsKey("height"))
        }

    @Test
    fun `updateUser returns deserialized response`() =
        runBlocking {
            val result = api.updateUser(name = "New Name")
            assertEquals("usr_abc123", result.id)
        }

    // --- helpers ---

    private fun lastRequestBodyJson() =
        Json
            .parseToJsonElement(
                (mockEngine.requestHistory.last().body as OutgoingContent.ByteArrayContent)
                    .bytes()
                    .decodeToString(),
            ).jsonObject

    companion object {
        internal val USER_NET_JSON =
            """
            {
                "id": "usr_abc123",
                "name": "Alex Smith",
                "sex": "MALE",
                "height": 178.0,
                "dateOfBirth": "1990-06-15",
                "activityLevel": "LIGHTLY_ACTIVE",
                "targetWeightKg": 80.0,
                "dailyDeficitKcal": 500.0,
                "targetProtein": 160.0,
                "targetCarbs": 200.0,
                "targetFat": 70.0,
                "createdAt": "2024-01-10T08:00:00Z",
                "updatedAt": "2024-03-20T14:30:00Z"
            }
            """.trimIndent()
    }
}
