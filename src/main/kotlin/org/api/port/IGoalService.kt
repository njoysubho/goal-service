package org.api.port

import org.api.domain.Goal

interface IGoalService {
    fun createGoal(goal: Goal): Goal
    fun getAll(): List<Goal>
}