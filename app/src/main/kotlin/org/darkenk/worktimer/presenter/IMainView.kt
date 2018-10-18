package org.darkenk.worktimer.presenter

interface IMainView {
    fun showIsWorking(working: Boolean)
    fun showWorkTime(time: String)
}
