package org.api.port

import org.api.domain.Category

interface ICategoryService {
    fun createCategory(category: Category): Category
    fun getCategoryById(id: String?): Category
    fun getAllCategories():List<Category>
}