package org.api.service.integration

import io.micronaut.http.HttpMethod
import io.micronaut.http.client.HttpClient
import io.micronaut.http.simple.SimpleHttpRequest
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.RestAssured.given
import io.restassured.RestAssured.port
import org.api.controller.dtos.CategoryCreationRequestDTO
import org.api.controller.dtos.CategoryCreationResponseDTO
import org.api.exception.ErrorCode
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import javax.inject.Inject

@MicronautTest(environments = ["db-test"])
class CategoryControllerIntegrationTest {

    @Inject
    lateinit var embeddedServer: EmbeddedServer

    @Inject
    lateinit var httpClient: HttpClient

    @BeforeEach
    fun setUp() {
        port = embeddedServer.port
    }

    @Test
    fun testCategoryCreation() {
        val categoryCreationRequest = CategoryCreationRequestDTO(name = "testCategory1")
        given()
            .header("content-type", "application/json")
            .body(categoryCreationRequest).log().all()
            .`when`().post("/v1/categories").prettyPeek()
            .then()
            .statusCode(201)
            .body("id", Matchers.notNullValue())
            .body("name", Matchers.`is`("testCategory1"))
            .body("createdOn", Matchers.notNullValue())
            .body("systemDefined", Matchers.`is`(false))
    }

    @Test
    fun testGetCategory() {
        val testCategoryName = "testCategory2"
        val id = createCategory("testCategory2")
        given()
            .`when`().get("/v1/categories/$id").prettyPeek()
            .then()
            .statusCode(200)
            .body("id", Matchers.`is`(id.toString()))
            .body("name", Matchers.`is`(testCategoryName))
            .body("createdOn", Matchers.notNullValue())
            .body("systemDefined", Matchers.`is`(false))

    }

    @Test
    fun itShouldThrowExceptionWhenNoCategoryFound() {
        val id = UUID.randomUUID()
        given()
            .`when`().get("/v1/categories/$id").prettyPeek()
            .then()
            .statusCode(404)
            .body("errorCode", Matchers.`is`(ErrorCode.NOT_FOUND.code))

    }

    private fun createCategory(name: String): UUID? {
        val categoryCreationRequest = CategoryCreationRequestDTO(name = name)
        val httpRequest = SimpleHttpRequest(
            HttpMethod.POST,
            "http://localhost:$port/v1/categories",
            categoryCreationRequest
        );
        val response = httpClient.toBlocking().exchange(httpRequest, CategoryCreationResponseDTO::class.java);
        val createdCategory = response.body();
        Assertions.assertNotNull(createdCategory);
        return createdCategory?.id
    }
}