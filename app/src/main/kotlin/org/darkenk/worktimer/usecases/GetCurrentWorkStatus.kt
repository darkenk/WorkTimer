package org.darkenk.worktimer.usecases

import org.darkenk.worktimer.domain.Repository
import org.darkenk.worktimer.domain.TimeProvider
import org.darkenk.worktimer.domain.WorkEntry

class GetCurrentWorkStatus(private val timeProvider: TimeProvider, private val repository: Repository) {
    
    enum class Status(val value: Int) {
        NOT_WORKING(0),
        WORKING(0)
    }
    
    data class WorkStatus(val duration: Long, val status: Status)
    
    fun getStatus(): WorkStatus {
        return WorkStatus(0, Status.WORKING)
    }
}