package org.api.service

import org.api.domain.Goal
import org.api.port.IGoalDAO
import org.api.port.IGoalService
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalService(@Inject val goalDAO: IGoalDAO) : IGoalService {
    override fun createGoal(goal: Goal): Goal {
        return goalDAO.createGoal(goal)
    }

    override fun getAll(): List<Goal> {
        return goalDAO.findAll()
    }

    override fun getGoalById(id: String): Goal {
        return goalDAO.findById(UUID.fromString(id))
    }

}