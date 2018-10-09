package org.darkenk.worktimer.usecases

import org.darkenk.worktimer.domain.EntryType
import org.darkenk.worktimer.domain.Repository
import org.darkenk.worktimer.domain.TimeProvider
import org.darkenk.worktimer.domain.WorkEntry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EndWorkTest {

    @Mock
    lateinit var timeProvider: TimeProvider

    @Mock
    lateinit var repository: Repository

    lateinit var endWork: EndWork

    @Before
    fun startup() {
        MockitoAnnotations.initMocks(this)
        endWork = EndWork(timeProvider, repository)
    }

    @Test
    fun endWorkAddsEntryToDatabase() {
        Mockito.`when`(timeProvider.getCurrentTime()).thenReturn(100)
        Mockito.`when`(repository.getLastEntry()).thenReturn(WorkEntry(EntryType.WorkStart, 99))
        
        endWork.endWork()

        Mockito.verify(repository).save(WorkEntry(EntryType.WorkEnd, 100))
    }

    @Test(expected = EndWork.EndWorkException::class)
    fun endWorkThrowsExceptionIfWorkWasNotStarted() {
        Mockito.`when`(timeProvider.getCurrentTime()).thenReturn(100)
        Mockito.`when`(repository.getLastEntry()).thenReturn(WorkEntry(EntryType.WorkEnd, 99))

        endWork.endWork()
    }

    @Test(expected = EndWork.EndWorkException::class)
    fun endThrowsExceptionIfLastEntryTimeIsHigherThenActualTime() {
        Mockito.`when`(timeProvider.getCurrentTime()).thenReturn(100)
        Mockito.`when`(repository.getLastEntry()).thenReturn(WorkEntry(EntryType.WorkStart, 101))
        
        endWork.endWork()

    }
}