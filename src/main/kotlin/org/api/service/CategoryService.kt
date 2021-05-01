package org.api.service

import org.api.domain.Category
import org.api.port.ICategoryDAO
import org.api.port.ICategoryService
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryService(
    @Inject val categoryDAO: ICategoryDAO
) : ICategoryService {

    override fun createCategory(category: Category): Category {
        return categoryDAO.save(category)
    }

    override fun getCategoryById(id: String?): Category {
        if (id == null) {
            throw IllegalArgumentException("category id cannot be null")
        }
        val uuid = UUID.fromString(id)
        return categoryDAO.findById(uuid);
    }

    override fun getAllCategories(): List<Category> {
        return categoryDAO.findAll();
    }
}