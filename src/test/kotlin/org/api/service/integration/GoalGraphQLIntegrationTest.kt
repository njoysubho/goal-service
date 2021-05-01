package org.api.service.integration

import io.micronaut.http.HttpMethod
import io.micronaut.http.client.HttpClient
import io.micronaut.http.simple.SimpleHttpRequest
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.RestAssured
import org.api.adaptor.controller.dtos.CategoryCreationRequestDTO
import org.api.adaptor.controller.dtos.CategoryCreationResponseDTO
import org.api.adaptor.controller.dtos.GoalCreationRequestDTO
import org.api.adaptor.controller.dtos.GoalCreationResponseDTO
import org.api.adaptor.repository.CategoryRepository
import org.api.adaptor.repository.GoalRepository
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import javax.inject.Inject

@MicronautTest(environments = ["db-test"])
class GoalGraphQLIntegrationTest {
    @Inject
    lateinit var embeddedServer: EmbeddedServer

    @Inject
    lateinit var goalRepository: GoalRepository

    @Inject

    lateinit var categoryRepository: CategoryRepository

    @Inject
    lateinit var httpClient: HttpClient

    @BeforeEach
    fun setUp() {
        RestAssured.port = embeddedServer.port
        goalRepository.deleteAll()
        categoryRepository.deleteAll()
    }


    @Test
    fun it_should_fetch_all_goals() {
        val categoryId = createCategory("category4")
        val id = createGoalWithCategory("testgoal4", categoryId!!);
        RestAssured.given()
            .body(
                """
                    {"query":"query {goalById(id:\"$id\"){name category{name}}}"}
                    """.trimIndent()
            )
            .contentType("application/json")
            .`when`().post("/graphql").prettyPeek()
            .then()
            .statusCode(200)
            .body("data.goalById.name", Matchers.`is`("testgoal4"))
    }

    private fun createGoalWithCategory(goalName: String, categoryId: UUID): UUID? {
        var goalCreationRequest = GoalCreationRequestDTO(name = goalName, categoryId = categoryId.toString())
        val httpRequest = SimpleHttpRequest(
            HttpMethod.POST,
            "http://localhost:${RestAssured.port}/v1/goals",
            goalCreationRequest
        );
        val response = httpClient.toBlocking().exchange(httpRequest, GoalCreationResponseDTO::class.java);
        val createdGoal = response.body();
        Assertions.assertNotNull(createdGoal);
        return createdGoal?.id
    }


    private fun createCategory(name: String): UUID? {
        val categoryCreationRequest = CategoryCreationRequestDTO(name = name)
        val httpRequest = SimpleHttpRequest(
            HttpMethod.POST,
            "http://localhost:${RestAssured.port}/v1/categories",
            categoryCreationRequest
        );
        val response = httpClient.toBlocking().exchange(httpRequest, CategoryCreationResponseDTO::class.java);
        val createdCategory = response.body();
        Assertions.assertNotNull(createdCategory);
        return createdCategory?.id
    }

}