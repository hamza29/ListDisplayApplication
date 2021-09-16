package com.application

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper


@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity() {

    protected abstract var layoutRes: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

        setContentView(layoutRes)
    }


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }




}



fun BaseActivity.go(target: Class<*>, intentCompletion: ((Intent) -> (Unit))? = null, shouldFinish: Boolean = false) {
    val intent = Intent(baseContext, target)
    intentCompletion?.let { it(intent) }
    startActivity(intent)
    if (shouldFinish) {
        this.finish()
    }
}


