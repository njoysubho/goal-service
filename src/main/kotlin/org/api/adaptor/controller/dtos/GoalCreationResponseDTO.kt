package org.api.adaptor.controller.dtos

import java.util.*

data class GoalCreationResponseDTO(
    val id: UUID,
    val name: String,
    val categoryId:String?,
    val createdOn: String,
    val modifiedOn: String?,
    val createdBy: String?
)