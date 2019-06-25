package com.example.besafe.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.besafe.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_foto.*
import android.Manifest
import androidx.appcompat.app.AlertDialog

class Foto : AppCompatActivity() {

    private val TAKE_PHOTO_REQUEST = 101
    private var mCurrentPhotoPath: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foto)
        btnfoto.setOnClickListener{permisos()}

    }

    private fun permisos() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object: PermissionListener{
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    launchCamera()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    AlertDialog.Builder(this@Foto)
                        .setTitle(R.string.storage_permission_rationale_title)
                        .setMessage(R.string.storage_permition_rationale_message)
                        .setNegativeButton(android.R.string.cancel,
                            { dialog, _ ->
                                dialog.dismiss()
                                token?.cancelPermissionRequest()
                            })
                        .setPositiveButton(android.R.string.ok,
                            { dialog, _ ->
                                dialog.dismiss()
                                token?.continuePermissionRequest()
                            })
                        .setOnDismissListener({ token?.cancelPermissionRequest() })
                        .show()}

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }


            })
    }

    private fun launchCamera() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
