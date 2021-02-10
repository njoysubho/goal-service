package org.api.service

import org.api.domain.Goal
import org.api.port.ICategoryDAO
import org.api.port.IGoalDAO
import org.api.port.IGoalService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalService(@Inject val goalDAO:IGoalDAO):IGoalService {
    override fun createGoal(goal: Goal): Goal {
        return goalDAO.createGoal(goal)
    }

    override fun getAll(): List<Goal> {
        return goalDAO.findAll()
    }

}