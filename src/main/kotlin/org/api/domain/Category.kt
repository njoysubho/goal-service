package org.api.domain

import java.time.OffsetDateTime
import java.util.*

data class Category(var id: UUID? = null, val name: String, val tags: String = "") {
    var createdBy: String = ""
    var createdOn: OffsetDateTime? = null
    var modifiedOn: OffsetDateTime? = null
    var systemDefined: Boolean = false
}