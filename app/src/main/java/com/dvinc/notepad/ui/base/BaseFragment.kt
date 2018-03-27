/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.ui.base

import android.support.annotation.LayoutRes
import butterknife.ButterKnife
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import butterknife.Unbinder

abstract class BaseFragment : Fragment() {

    private var unbinder: Unbinder? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getFragmentLayoutId(), container, false)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder?.unbind()
    }

    /**
     * Getting fragment layout resource id.
     *
     * @return - fragment layout id.
     */
    @LayoutRes
    abstract fun getFragmentLayoutId(): Int
}
