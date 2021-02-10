package org.api.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import org.api.controller.dtos.CategoryCreationRequestDTO
import org.api.controller.dtos.CategoryCreationResponseDTO
import org.api.domain.Category
import org.api.port.ICategoryService
import javax.inject.Inject

@Controller
class CategoryController(@Inject val categoryService: ICategoryService) {
    @Post(uri = "/v1/categories")
    @Consumes
    @Produces
    fun createCategory(@Body categoryCreationRequest: CategoryCreationRequestDTO)
            : HttpResponse<CategoryCreationResponseDTO> {
        val toBeCreatedCategory = toDomainCategory(categoryCreationRequest)
        val createdCategory = categoryService.createCategory(toBeCreatedCategory)
        return HttpResponse.created(toResponse(createdCategory))
    }

    @Get(uri = "/v1/categories/{id}")
    @Produces
    fun getCategoryById(@PathVariable("id") id: String?): HttpResponse<Category> {
        return HttpResponse.ok(categoryService.getCategoryById(id))
    }

    private fun toResponse(createdCategory: Category): CategoryCreationResponseDTO {
        return CategoryCreationResponseDTO(
            id = createdCategory.id!!,
            name = createdCategory.name,
            tags = createdCategory.tags,
            createdBy = createdCategory.createdBy,
            createdOn = createdCategory.createdOn.toString(),
            modifiedOn = createdCategory.modifiedOn.toString(),
            systemDefined = createdCategory.systemDefined
        )
    }

    private fun toDomainCategory(categoryCreationRequest: CategoryCreationRequestDTO): Category {
        return Category(name = categoryCreationRequest.name, tags = categoryCreationRequest.tags)
    }
}