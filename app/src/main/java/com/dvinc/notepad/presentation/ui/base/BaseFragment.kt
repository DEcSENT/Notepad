/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.dvinc.notepad.common.snackbar.SnackbarFactory

abstract class BaseFragment : Fragment(), BaseView {

    private val decorView by lazy { requireActivity().window.decorView }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getFragmentLayoutId(), container, false)
    }

    /**
     * Getting fragment layout resource id.
     *
     * @return - fragment layout id.
     */
    @LayoutRes
    abstract fun getFragmentLayoutId(): Int


    override fun showMessage(messageResId: Int, containerResId: Int, anchorView: View?, duration: Int) {
        showSnackBar(messageResId, containerResId, anchorView?.id, duration)
    }

    override fun showErrorMessage(messageResId: Int, containerResId: Int, anchorViewId: Int?, duration: Int) {
        //todo add red background color
        showSnackBar(messageResId, containerResId, anchorViewId, duration)
    }

    private fun showSnackBar(messageResId: Int, containerResId: Int, anchorViewId: Int?, duration: Int) {
        val message = getString(messageResId)
        val snackbar = SnackbarFactory.create(
            mainView = decorView,
            messageText = message,
            containerResId = containerResId,
            duration = duration
        )
        anchorViewId?.let { snackbar?.setAnchorView(it) }
        snackbar?.show()
    }
}
