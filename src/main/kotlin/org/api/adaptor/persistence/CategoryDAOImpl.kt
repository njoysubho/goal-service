package org.api.adaptor.persistence

import org.api.adaptor.persistence.entity.CategoryEntity
import org.api.adaptor.repository.CategoryRepository
import org.api.domain.Category
import org.api.exception.CategoryNotFoundException
import org.api.port.ICategoryDAO
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class CategoryDAOImpl(
    @Inject val categoryRepository: CategoryRepository
) : ICategoryDAO {

    override fun save(category: Category): Category {
        var categoryEntity = CategoryEntity(category.name)
        //TODO
        categoryEntity.createdBy = "admin"
        categoryEntity = categoryRepository.save(categoryEntity)
        return toDomainCategory(categoryEntity)
    }

    override fun findById(id: UUID): Category {
        val categoryEntity = categoryRepository.findById(id)
            .orElseThrow { CategoryNotFoundException("category with $id is not found") }
        return toDomainCategory(categoryEntity)
    }

    override fun findByCategoryName(categoryName: String): Category {
        val categoryEntity = categoryRepository.findByCategoryName(categoryName)
            .orElseThrow { CategoryNotFoundException("category with $categoryName is not found") }
        return toDomainCategory(categoryEntity)
    }

    private fun toDomainCategory(categoryEntity: CategoryEntity): Category {
        val categoryDomain = Category(
            name = categoryEntity.categoryName,
            id = categoryEntity.id,
            tags = categoryEntity.tags,
        )
        categoryDomain.createdBy = categoryEntity.createdBy
        categoryDomain.createdOn = categoryEntity.createdOn
        categoryDomain.modifiedOn = categoryEntity.modifiedOn
        categoryDomain.systemDefined = categoryEntity.systemDefined
        return categoryDomain
    }

}