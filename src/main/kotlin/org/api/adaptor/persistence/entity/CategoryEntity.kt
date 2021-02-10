package org.api.adaptor.persistence.entity

import io.micronaut.data.annotation.DateCreated
import java.time.OffsetDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "category", schema = "goal")
class CategoryEntity(val categoryName: String) {
    @Id
    @GeneratedValue
    var id: UUID? = null
    var systemDefined: Boolean = false
    var tags: String = ""
    var createdBy: String = ""

    @DateCreated
    var createdOn: OffsetDateTime? = null
    var modifiedOn: OffsetDateTime? = null
}