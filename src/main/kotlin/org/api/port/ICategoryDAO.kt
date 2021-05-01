package org.api.port

import org.api.domain.Category
import java.util.*

interface ICategoryDAO {
    fun save(category: Category): Category
    fun findById(uuid: UUID): Category
    fun findByCategoryName(categoryName: String): Category
    fun findAll():List<Category>
}