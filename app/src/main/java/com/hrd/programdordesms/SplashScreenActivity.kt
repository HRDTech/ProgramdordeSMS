package com.hrd.programdordesms

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog

class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long=8000 // 8 sec
    private val RECORD_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var actionBar = supportActionBar

        if(null != actionBar){
            actionBar.hide()
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val permissionCamera: Int = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            val permissionContact: Int = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)

            if (permissionCamera != PackageManager.PERMISSION_GRANTED && permissionContact != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS), RECORD_REQUEST_CODE)
            }else if (permissionCamera != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.CAMERA), RECORD_REQUEST_CODE)
            }else if ( permissionContact != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), RECORD_REQUEST_CODE)
            }else{
                nextApk()
            }
        }else{
            nextApk()
        }
    }

    private fun nextApk(){
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (RECORD_REQUEST_CODE == requestCode){
            var granted: Boolean = true

            for (result: Int in grantResults){
                if (result != PackageManager.PERMISSION_GRANTED){
                    granted = false
                }
            }

            if (granted){
                nextApk()
            }else{
                showDialogError().show()
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun showDialogError(): AlertDialog {
        var alertDialog = AlertDialog.Builder(this)
            .setTitle("Error ....")
            .setMessage("El dialogo de solicitud de permiso tiene que permitirse. ")

        return alertDialog.create()
    }
}
