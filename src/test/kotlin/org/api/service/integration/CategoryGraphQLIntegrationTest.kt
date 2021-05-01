package org.api.service.integration

import io.micronaut.http.HttpMethod
import io.micronaut.http.client.HttpClient
import io.micronaut.http.simple.SimpleHttpRequest
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.RestAssured.given
import io.restassured.RestAssured.port
import io.restassured.http.ContentType
import org.api.adaptor.controller.dtos.CategoryCreationRequestDTO
import org.api.adaptor.controller.dtos.CategoryCreationResponseDTO
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import javax.inject.Inject

@MicronautTest(environments = ["db-test"])
class CategoryGraphQLIntegrationTest {

    @Inject
    lateinit var embeddedServer: EmbeddedServer

    @Inject
    lateinit var httpClient: HttpClient

    @BeforeEach
    fun setUp() {
        port = embeddedServer.port
    }


    @Test
    fun testGetCategory() {
        val testCategoryName = "testCategory2"
        val id = createCategory("testCategory2")
        given()
            .body(
                """
                    {"query":"query {categories{name}}"}
                    """.trimIndent()
            )
            .contentType("application/json")
            .`when`().post("/graphql").prettyPeek()
            .then()
            .statusCode(200)
            .body("data.categories[0].name", Matchers.`is`(testCategoryName))

    }

    @Test
    fun testGetCategoryById() {
        val testCategoryName = "testCategory3"
        val id = createCategory("testCategory3")
        given()
            .body(
                """
                    {"query":"query {categoryById(id:\"$id\"){name}}"}
                    """.trimIndent()
            )
            .contentType("application/json")
            .`when`().post("/graphql").prettyPeek()
            .then()
            .statusCode(200)
            .body("data.categoryById.name", Matchers.`is`(testCategoryName))

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