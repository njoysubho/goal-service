package org.api.adaptor.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import org.api.adaptor.controller.dtos.GoalCreationRequestDTO
import org.api.adaptor.controller.dtos.GoalCreationResponseDTO
import org.api.domain.Category
import org.api.domain.Goal
import org.api.service.GoalService
import java.util.*
import javax.inject.Inject

@Controller
class GoalController(@Inject val goalService: GoalService) {
    @Post("/v1/goals")
    @Produces
    @Consumes
    fun createGoal(@Body goalCreationRequestDTO: GoalCreationRequestDTO): HttpResponse<GoalCreationResponseDTO> {
        val goal = Goal(
            name = goalCreationRequestDTO.name,
            categoryId = goalCreationRequestDTO.categoryId?.let { it-> UUID.fromString(it) }
        )
        val createdGoal = goalService.createGoal(goal)
        return HttpResponse.created(toResponse(createdGoal))
    }

    @Get("/v1/goals")
    @Produces
    fun getAllGoals(): HttpResponse<List<Goal>> {
        val fetchedGoals = goalService.getAll()
        return HttpResponse.ok(fetchedGoals)
    }


    private fun toResponse(createdGoal: Goal): GoalCreationResponseDTO {
        val goalCreationResponse = GoalCreationResponseDTO(
            id = createdGoal.id!!,
            name = createdGoal.name,
            categoryId = createdGoal.categoryId?.toString(),
            createdOn = createdGoal.createdOn.toString(),
            modifiedOn = createdGoal.modifiedOn.toString(),
            createdBy = createdGoal.createdBy
        )
        return goalCreationResponse
    }


}