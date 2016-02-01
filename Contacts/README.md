About Contacts
==============

Simple contacts demo app, implemented using Android data binding with MVVM design pattern.


Features
========

Displays a list of contacts, contact details and adding of new contacts.
Supports pull-to-refresh data updates.


Changelog
=========

1.0.0.0 - Initial release


ContactsConfig.java
------------------

This is the main configuration file and there are some important constants: addresses to API endpoints, API keys to 3rd party services etc.


build.gradle
------------

This is the main build script.

See [Versioning Your Applications](http://developer.android.com/tools/publishing/versioning.html#appversioning) in Android documentation for more info.


Dependencies
============

* [AndroidViewModel](https://github.com/inloop/AndroidViewModel)
* [Android Support Library v4](http://developer.android.com/tools/extras/support-library.html)
* [AppCompat](https://developer.android.com/reference/android/support/v7/appcompat/package-summary.html)
* [GSON](http://code.google.com/p/google-gson/)
* [Otto](https://github.com/square/otto)
* [Picasso](https://github.com/square/picasso)
* [Retrofit](https://github.com/square/retrofit)
* [OkHttp](http://square.github.io/okhttp)

Testing
=======

* Test app on different Android versions (handset, tablet)
* Test overdraws
* Test offline/empty/progress states
* Test migration from old version to the new one
* Test slow internet connection
* Test reboot (boot receivers, alarms, services)
* Test analytics tracking
* Monkey test (fast clicking, changing orientation)


Publishing
==========

* Set proper versions in the main build script
* Check build config fields in the main build script
* Update text info in changelog/about/help
* Add analytics events for new features


Developed by
============

[Ondrej Ruttkay](https://www.ondrejruttkay.com)


License
=======

    Copyright 2015 Ondrej Ruttkay

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
