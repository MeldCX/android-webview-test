package com.example.webviewvideotest

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.layout.*
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.layout)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR


        WebView.setWebContentsDebuggingEnabled(true)
        backgroundWebView.settings.javaScriptEnabled = true
        backgroundWebView.settings.domStorageEnabled = true
        backgroundWebView.clearCache(true)

        clientWebView.settings.javaScriptEnabled = true
        clientWebView.settings.domStorageEnabled = true
        clientWebView.settings.allowFileAccess = true
        clientWebView.settings.allowFileAccessFromFileURLs = true
        clientWebView.settings.allowContentAccess = true
        clientWebView.settings.allowUniversalAccessFromFileURLs = true

        clientWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                view?.requestFocus()
                super.onPageFinished(view, url)
            }
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1234)
            return
        }else{
            loadHtml()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1234 -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    loadHtml()
                } else {

                }
                return
            }


            else -> {

            }
        }
    }

    fun loadHtml(){
        val reader = BufferedReader(InputStreamReader(assets.open("test.html"), "UTF-8"))
        val testhtml = StringBuffer()
        var line : String? = ""
        while (line != null) {
            line = reader.readLine()
            testhtml.append(line)
        }
        clientWebView.loadDataWithBaseURL("file:///mnt/sdcard/", testhtml.toString(), "text/html", "UTF-8", null)

    }


}
