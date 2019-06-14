/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.dvinc.notepad.R
import com.dvinc.notepad.common.snackbar.SnackbarFactory

abstract class BaseFragment : Fragment(), BaseView {

    private val decorView by lazy { requireActivity().window.decorView }

    /**
     * Getting fragment layout resource id.
     *
     * @return - fragment layout id.
     */
    @LayoutRes
    abstract fun getFragmentLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getFragmentLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Important fix for window insets
        // See info at https://medium.com/androiddevelopers/windows-insets-fragment-transitions-9024b239a436
        ViewCompat.requestApplyInsets(view)
    }

    override fun showMessage(messageResId: Int, containerResId: Int, anchorView: View?, duration: Int) {
        val backgroundColorId = R.color.black
        val textColorId = R.color.white
        showSnackBar(messageResId, containerResId, anchorView?.id, duration, backgroundColorId, textColorId)
    }

    override fun showErrorMessage(messageResId: Int, containerResId: Int, anchorView: View?, duration: Int) {
        val backgroundColorId = R.color.red
        val textColorId = R.color.white
        showSnackBar(messageResId, containerResId, anchorView?.id, duration, backgroundColorId, textColorId)
    }

    fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun showSnackBar(
        messageResId: Int,
        containerResId: Int,
        anchorViewId: Int?,
        duration: Int,
        @ColorRes backgroundColor: Int,
        @ColorRes textColor: Int
    ) {
        val message = getString(messageResId)
        val snackbar = SnackbarFactory.create(
            mainView = decorView,
            messageText = message,
            containerResId = containerResId,
            duration = duration,
            backgroundColor = backgroundColor,
            textColor = textColor
        )
        anchorViewId?.let { snackbar?.setAnchorView(it) }
        snackbar?.show()
    }
}
