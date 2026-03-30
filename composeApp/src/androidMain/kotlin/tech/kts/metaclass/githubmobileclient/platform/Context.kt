package tech.kts.metaclass.githubmobileclient.platform

import android.content.Context

lateinit var appContext: Context
    private set

fun initContext(context: Context) {
    appContext = context.applicationContext
}