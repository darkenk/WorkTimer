package org.darkenk.worktimer.domain


interface Repository {
    fun getLastEntry(): WorkEntry
    fun save(workEntry: WorkEntry)
    fun getEntries(from: Long, to: Long): List<WorkEntry>
}

enum class EntryType(val type: Int) {
    Empty(0), // special case, there was entry in database (only valid during first start of app)
    WorkStart(1),
    WorkEnd(2),
    AbsenceStart(3),
    AbsenceEnd(4)
}

data class WorkEntry(val type: EntryType, val time: Long)
