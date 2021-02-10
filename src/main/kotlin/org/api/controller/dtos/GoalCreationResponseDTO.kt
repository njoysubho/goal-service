package org.api.controller.dtos

import java.util.*

data class GoalCreationResponseDTO(
    val id: UUID,
    val name: String,
    val categoryName:String?,
    val createdOn: String,
    val modifiedOn: String?,
    val createdBy: String
)