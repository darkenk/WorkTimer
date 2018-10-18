package org.darkenk.worktimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.darkenk.worktimer.data.InMemoryRepository
import org.darkenk.worktimer.data.RealTimeProvider
import org.darkenk.worktimer.domain.Repository
import org.darkenk.worktimer.domain.TimeProvider
import org.darkenk.worktimer.presenter.IMainView
import org.darkenk.worktimer.presenter.WorkPresenter
import org.darkenk.worktimer.usecases.EndWork
import org.darkenk.worktimer.usecases.GetCurrentWorkStatus
import org.darkenk.worktimer.usecases.GetCurrentWorkTime
import org.darkenk.worktimer.usecases.StartWork

class MainActivity : AppCompatActivity(), IMainView {

    override fun showWorkTime(time: String) {
        tv_current_work_time.text = time
    }

    override fun showIsWorking(working: Boolean) {
        tv_work_status.text = if (working) getString(R.string.working_status) else getString(R.string.not_working_status)
        bt_start_stop_work.text = if (working) getString(R.string.end_work) else getString(R.string.start_work)
    }

    private lateinit var workPresenter: WorkPresenter
    
    private val timeProvider: TimeProvider = RealTimeProvider()
    
    private val repository: Repository = InMemoryRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        workPresenter = WorkPresenter(EndWork(timeProvider, repository), GetCurrentWorkStatus(repository),
                StartWork(timeProvider, repository), GetCurrentWorkTime(timeProvider, repository), this)
        
        workPresenter.init()
        
        bt_start_stop_work.setOnClickListener { workPresenter.startStopWorking() }
    }
}
