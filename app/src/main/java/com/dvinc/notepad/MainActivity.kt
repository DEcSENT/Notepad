package com.dvinc.notepad

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.dvinc.notepad.ui.notepad.NotepadFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragment(NotepadFragment(), NotepadFragment.TAG)
    }

    private fun showFragment(fragment: Fragment, fragmentTag: String) {
        val fragmentManager = supportFragmentManager
        fragmentManager?.
                beginTransaction()?.
                replace(R.id.fragment_container, fragment, fragmentTag)?.
                commit()
    }
}
