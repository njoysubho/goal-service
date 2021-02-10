package org.api.adaptor.repository

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import org.api.adaptor.persistence.entity.GoalEntity
import java.util.*

@Repository
interface GoalRepository : JpaRepository<GoalEntity, UUID>