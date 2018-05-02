package com.dvinc.notepad.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.dvinc.notepad.R
import com.dvinc.notepad.ui.note.NoteFragment
import com.dvinc.notepad.ui.notepad.NotepadFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showAndReplaceFragment(NotepadFragment(), NotepadFragment.TAG)
    }

    override fun onBackPressed() {
        val count = fragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            fragmentManager.popBackStack()
        }
    }

    //TODO: Think about good navigation
    fun showAndReplaceFragment(fragment: Fragment, fragmentTag: String) {
        val fragmentManager = supportFragmentManager
        fragmentManager?.
                beginTransaction()?.
                replace(R.id.fragmentContainer, fragment, fragmentTag)?.
                commit()
    }

    fun showAndAddFragment(fragment: Fragment, fragmentTag: String) {
        val fragmentManager = supportFragmentManager
        fragmentManager?.
                beginTransaction()?.
                replace(R.id.fragmentContainer, fragment, fragmentTag)?.
                addToBackStack(NoteFragment.TAG)?.
                commit()
    }
}
