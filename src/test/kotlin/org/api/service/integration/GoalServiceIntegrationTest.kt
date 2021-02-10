package org.api.service.integration

import io.micronaut.http.HttpMethod
import io.micronaut.http.client.HttpClient
import io.micronaut.http.simple.SimpleHttpRequest
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.RestAssured
import org.api.adaptor.repository.CategoryRepository
import org.api.adaptor.repository.GoalRepository
import org.api.controller.dtos.CategoryCreationRequestDTO
import org.api.controller.dtos.CategoryCreationResponseDTO
import org.api.controller.dtos.GoalCreationRequestDTO
import org.api.exception.ErrorCode
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import javax.inject.Inject

@MicronautTest(environments = ["db-test"])
class GoalServiceIntegrationTest {
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
    fun it_should_create_a_goal() {
        val categoryCreationRequest = GoalCreationRequestDTO(name = "testgoal1")
        RestAssured.given()
            .header("content-type", "application/json")
            .body(categoryCreationRequest).log().all()
            .`when`().post("/v1/goals").prettyPeek()
            .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("name", `is`("testgoal1"))
            .body("createdOn", notNullValue())
    }

    @Test
    fun it_should_create_a_goal_with_category() {
        val categoryId = createCategory("category2")
        var goalCreationRequest = GoalCreationRequestDTO(name = "testgoal2", "category2")
        RestAssured.given()
            .header("content-type", "application/json")
            .body(goalCreationRequest).log().all()
            .`when`().post("/v1/goals").prettyPeek()
            .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("name", `is`("testgoal2"))
            .body("categoryName", `is`("category2"))
            .body("createdOn", notNullValue())
    }

    @Test
    fun it_should_throw_exception_when_create_a_goal_with_non_existant_category() {
        var goalCreationRequest = GoalCreationRequestDTO(name = "testgoal3", "category3")
        RestAssured.given()
            .header("content-type", "application/json")
            .body(goalCreationRequest).log().all()
            .`when`().post("/v1/goals").prettyPeek()
            .then()
            .statusCode(404)
            .body("errorCode", `is`(ErrorCode.NOT_FOUND.code))
    }

    @Test
    fun it_should_fetch_all_goals() {
        createCategory("category4")
        createGoalWithCategory("testgoal4","category4");
        createGoalWithCategory("testgoal5","category4");
        createGoalWithCategory("testgoal6","category4");
        RestAssured
            .`when`().get("/v1/goals").prettyPeek()
            .then()
            .statusCode(200)
            .body("size()",`is`(3))
    }

    private fun createGoalWithCategory(goalName:String,categoryName:String) {
        var goalCreationRequest = GoalCreationRequestDTO(name = goalName, categoryName)
        RestAssured.given()
            .header("content-type", "application/json")
            .body(goalCreationRequest).log().all()
            .`when`().post("/v1/goals")
            .then()
            .statusCode(201)
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