package org.api.port

import org.api.domain.Goal
import java.util.*


interface IGoalDAO {
    fun createGoal(goal: Goal): Goal
    fun findAll(): List<Goal>
    fun findById(id:UUID): Goal
}