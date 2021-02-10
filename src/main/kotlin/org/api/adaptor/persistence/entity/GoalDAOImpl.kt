package org.api.adaptor.persistence.entity

import org.api.adaptor.repository.CategoryRepository
import org.api.adaptor.repository.GoalRepository
import org.api.domain.Goal
import org.api.exception.CategoryNotFoundException
import org.api.port.IGoalDAO
import java.util.stream.Collectors
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class GoalDAOImpl(
    @Inject val goalRepository: GoalRepository,
    @Inject val categoryRepository: CategoryRepository
) : IGoalDAO {
    override fun createGoal(goal: Goal): Goal {
        val categoryEntity = goal.categoryName?.let {
            categoryRepository.findByCategoryName(goal.categoryName)
                .orElseThrow { CategoryNotFoundException("category with ${goal.categoryName} is not found") }
        }
        var goalEntity = GoalEntity(goal.name)
        if (categoryEntity != null) {
            goalEntity.category = categoryEntity
        }
        val savedGoalEntity = goalRepository.save(goalEntity)
        goal.id = savedGoalEntity.id
        goal.createdOn = savedGoalEntity.createdOn
        goal.modifiedOn = savedGoalEntity.modifiedOn
        return goal
    }

    @Transactional
    override fun findAll(): List<Goal> {
        return toGoalDomains(goalRepository.findAll())
    }

    private fun toGoalDomains(goalEntities: List<GoalEntity>): List<Goal> {
        return goalEntities
            .map { toGoalDomain(it)}
    }

    private fun toGoalDomain(goalEntity: GoalEntity): Goal {
        val goal = Goal(
            id = goalEntity.id,
            categoryName = goalEntity.category.categoryName,
            name = goalEntity.name,
            createdBy = goalEntity.createdBy,
            createdOn = goalEntity.createdOn,
            modifiedOn = goalEntity.modifiedOn
        )
        return goal
    }

}