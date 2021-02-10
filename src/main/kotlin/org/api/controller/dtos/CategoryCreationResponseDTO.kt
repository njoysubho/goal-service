package org.api.controller.dtos

import java.util.*

data class CategoryCreationResponseDTO(
    val id: UUID,
    val name: String,
    val tags: String?,
    val createdOn: String,
    val modifiedOn: String?,
    val createdBy: String,
    val systemDefined: Boolean
)