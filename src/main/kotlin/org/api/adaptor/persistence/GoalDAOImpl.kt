package org.api.adaptor.persistence

import org.api.adaptor.persistence.entity.CategoryEntity
import org.api.adaptor.persistence.entity.GoalEntity
import org.api.adaptor.repository.CategoryRepository
import org.api.adaptor.repository.GoalRepository
import org.api.domain.Category
import org.api.domain.Goal
import org.api.exception.NotFoundException
import org.api.port.IGoalDAO
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class GoalDAOImpl(
    @Inject val goalRepository: GoalRepository,
    @Inject val categoryRepository: CategoryRepository
) : IGoalDAO {
    override fun createGoal(goal: Goal): Goal {
        val categoryEntity = goal.categoryId?.let {
            categoryRepository.findById(goal.categoryId)
                .orElseThrow { NotFoundException("category with ${goal.categoryId} is not found") }
        }
        val goalEntity = GoalEntity(goal.name)
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

    @Transactional
    override fun findById(id: UUID): Goal {
        return toGoalDomain(goalRepository.findById(id)
            .orElseThrow { NotFoundException("Goal with id $id not found") })
    }

    private fun toGoalDomains(goalEntities: List<GoalEntity>): List<Goal> {
        return goalEntities
            .map { toGoalDomain(it) }
    }

    private fun toGoalDomain(goalEntity: GoalEntity): Goal {
        val goal = Goal(
            id = goalEntity.id,
            categoryId = goalEntity.category.id,
            name = goalEntity.name,
            createdBy = goalEntity.createdBy,
            createdOn = goalEntity.createdOn,
            modifiedOn = goalEntity.modifiedOn
        )
        return goal
    }

    private fun toCateGoryDomain(categoryEntity: CategoryEntity): Category {
        return Category(id = categoryEntity.id, name = categoryEntity.categoryName)
    }

}