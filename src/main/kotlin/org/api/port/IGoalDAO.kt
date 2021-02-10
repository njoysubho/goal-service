package org.api.port

import org.api.domain.Goal


interface IGoalDAO {
    fun createGoal(goal: Goal): Goal
    fun findAll(): List<Goal>
}