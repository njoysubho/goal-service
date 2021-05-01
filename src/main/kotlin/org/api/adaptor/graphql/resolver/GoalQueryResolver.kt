package org.api.adaptor.graphql.resolver

import graphql.kickstart.tools.GraphQLResolver
import org.api.adaptor.graphql.model.Category
import org.api.adaptor.graphql.model.Goal
import org.api.port.ICategoryService
import javax.inject.Singleton

@Singleton
class GoalQueryResolver(val categoryService: ICategoryService) : GraphQLResolver<Goal> {

    fun category(goal: Goal): Category {
        val category = categoryService.getCategoryById(goal.categoryId)
        return Category(id = category.id.toString(), name = category.name, tags = category.tags)
    }
}