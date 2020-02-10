# **Notepad**
[![CircleCI](https://circleci.com/gh/DEcSENT/Notepad/tree/master.svg?style=svg)](https://circleci.com/gh/DEcSENT/Notepad/tree/master)
[![codecov](https://codecov.io/gh/DEcSENT/Notepad/branch/master/graph/badge.svg)](https://codecov.io/gh/DEcSENT/Notepad)
[![codebeat badge](https://codebeat.co/badges/62f7e4cf-c3e2-41c1-be0f-4c1864082b94)](https://codebeat.co/projects/github-com-decsent-notepad-master)

This is an infinity sandbox for new technologies and ideas.

The project has been rewritten many times.

Notepad application for fast and simple writing and interaction with notes.
**Work in progress.**

## Technology stack

### Language

- Kotlin

### Libraries

- Dagger 2 + Dagger Assisted Inject
- ~~RxJava 2~~ Kotlin Coroutines
- Room
- Stetho
- Android X
- Navigation Architecture Component
- SafeArgs

### Architecture

- Clean architecture
- Multi-modular structure
- ViewModel with ViewState on presentation layer

### Tools

- Detekt checks on every CI build
- [Danger](https://danger.systems/ruby/) with [Android Lint plugin](https://github.com/workivate/danger-android_lint) for PR process automation
- Jacoco with multi-modular coverage (thanks to [Android Root Coverage Plugin](https://github.com/NeoTech-Software/Android-Root-Coverage-Plugin))
- Codebeat static analysis
- Spotless plugin (manual run)

### Other

- Dark theme
- Unit tests
- Circle CI

## What next

- Note tags
- Notes archive
- UI tests
- Fingerprint
- Security
- Notes export

## Screens

** For now this screens are outdated. Will be updated soon.**

<img src="https://raw.githubusercontent.com/DEcSENT/cv/master/screens/notepad1.png" width="335" height="602"><img src="https://raw.githubusercontent.com/DEcSENT/cv/master/screens/notepad4.png" width="335" height="602">
<img src="https://raw.githubusercontent.com/DEcSENT/cv/master/screens/notepad3.png" width="335" height="602"><img src="https://raw.githubusercontent.com/DEcSENT/cv/master/screens/notepad2.png" width="335" height="602">

## License

MIT License

Copyright (c) 2018 - 2020 Denis Verentsov

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
