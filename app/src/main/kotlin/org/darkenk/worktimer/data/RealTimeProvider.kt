package org.darkenk.worktimer.data

import org.darkenk.worktimer.domain.TimeProvider
import java.util.*

class RealTimeProvider : TimeProvider {

    override fun getCurrentTime(): Long {
        return Calendar.getInstance().timeInMillis
    }
}