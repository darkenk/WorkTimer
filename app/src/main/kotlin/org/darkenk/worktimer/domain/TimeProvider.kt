package org.darkenk.worktimer.domain

interface TimeProvider {
    fun getCurrentTime(): Long
}