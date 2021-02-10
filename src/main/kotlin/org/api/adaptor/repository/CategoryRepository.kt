package org.api.adaptor.repository

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import org.api.adaptor.persistence.entity.CategoryEntity
import java.util.*

@Repository
interface CategoryRepository : JpaRepository<CategoryEntity, UUID> {
    fun findByCategoryName(categoryName: String): Optional<CategoryEntity>
}