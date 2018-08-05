/*
 * Copyright (c) 2018 by Denis Verentsov (decsent@Yandex.ru)
 * All rights reserved.
 */

package com.dvinc.notepad.presentation.base

import android.support.annotation.LayoutRes
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View

abstract class BaseFragment : Fragment() {

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
}
