package org.api.adaptor.persistence.entity

import io.micronaut.data.annotation.DateCreated
import java.time.OffsetDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "goal", schema = "goal")
class GoalEntity(val name: String) {
    @Id
    @GeneratedValue
    var id: UUID? = null
    var createdBy: String = ""

    @ManyToOne(fetch = FetchType.LAZY)
    lateinit var category: CategoryEntity

    @DateCreated
    var createdOn: OffsetDateTime? = null
    var modifiedOn: OffsetDateTime? = null
}