package org.darkenk.worktimer.usecases

import org.darkenk.worktimer.domain.EntryType
import org.darkenk.worktimer.domain.Repository
import org.darkenk.worktimer.domain.TimeProvider
import org.darkenk.worktimer.domain.WorkEntry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class StartWorkTest {
    
    @Mock
    lateinit var timeProvider: TimeProvider
    
    @Mock
    lateinit var repository: Repository
    
    lateinit var startWork: StartWork
    
    @Before
    fun startup() {
        MockitoAnnotations.initMocks(this)
        startWork = StartWork(timeProvider, repository)
    }
    
    @Test
    fun startWorkAddsEntryToDatabaseForTheFirstTime() {
        `when`(timeProvider.getCurrentTime()).thenReturn(100)
        `when`(repository.getLastEntry()).thenReturn(WorkEntry(EntryType.Empty, 0))

        startWork.startWork()

        verify(repository).save(WorkEntry(EntryType.WorkStart, 100))
    }
    
    @Test(expected = StartWork.StartWorkException::class)
    fun startWorkThrowsExceptionIfWorkWasAlreadyStarted() {
        `when`(timeProvider.getCurrentTime()).thenReturn(100)
        `when`(repository.getLastEntry()).thenReturn(WorkEntry(EntryType.WorkStart, 99))

        startWork.startWork()
    }
    
    @Test(expected = StartWork.StartWorkException::class)
    fun startWorkThrowsExceptionIfLastEntryTimeIsHigherThenActualTime() {
        `when`(timeProvider.getCurrentTime()).thenReturn(100)
        `when`(repository.getLastEntry()).thenReturn(WorkEntry(EntryType.WorkEnd, 101))

        startWork.startWork()

    }
}