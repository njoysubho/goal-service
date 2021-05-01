package org.api.adaptor.controller.dtos

import java.util.*

data class CategoryCreationResponseDTO(
    val id: UUID,
    val name: String,
    val tags: String?,
    val createdOn: String,
    val modifiedOn: String?,
    val createdBy: String="admin",
    val systemDefined: Boolean
)