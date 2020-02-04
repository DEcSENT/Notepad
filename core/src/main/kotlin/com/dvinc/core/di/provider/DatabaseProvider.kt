/*
 * Copyright (c) 2020 by Denis Verentsov (decsent@yandex.ru)
 * All rights reserved.
 */

package com.dvinc.core.di.provider

import com.dvinc.core.database.NotepadDatabase

interface DatabaseProvider {

    fun provideNotepadDatabase(): NotepadDatabase
}
