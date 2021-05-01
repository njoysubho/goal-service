package org.api.adaptor.graphql.resolver

import graphql.kickstart.tools.GraphQLQueryResolver
import org.api.adaptor.graphql.model.Category
import org.api.adaptor.graphql.model.Goal
import org.api.port.ICategoryService
import org.api.port.IGoalService
import javax.inject.Singleton

@Singleton
class QueryResolver(
    private val categoryService: ICategoryService,
    private val goalService: IGoalService
) : GraphQLQueryResolver {
    fun categories(): List<Category> {
        val categoryDomains = categoryService.getAllCategories()
        return categoryDomains.map { category ->
            Category(
                name = category.name, tags = category.tags, id = category.id.toString()
            )
        }
    }

    fun categoryById(id: String): Category {
        val categoryDomain = categoryService.getCategoryById(id);
        return Category(
            name = categoryDomain.name, tags = categoryDomain.tags, id = categoryDomain.id.toString()
        )
    }

    fun goalById(id: String): Goal {
        val retrievedGoal = goalService.getGoalById(id)
        return Goal(
            id = retrievedGoal.id.toString(), name = retrievedGoal.name,
            categoryId = retrievedGoal.categoryId?.toString()
        )
    }
}