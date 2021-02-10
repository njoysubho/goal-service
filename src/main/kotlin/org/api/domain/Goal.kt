package org.api.domain

import java.time.OffsetDateTime
import java.util.*

data class Goal(
    var id: UUID? = null,
    val categoryName: String? = null,
    val name: String,
    val createdBy: String = "",
    val category: String = "",
    var createdOn: OffsetDateTime? = null,
    var modifiedOn: OffsetDateTime? = null
)