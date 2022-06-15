package com.example.coordikidsapp.ui

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class CoordiKidsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(applicationContext);
        AppEventsLogger.activateApp(this);
    }
}