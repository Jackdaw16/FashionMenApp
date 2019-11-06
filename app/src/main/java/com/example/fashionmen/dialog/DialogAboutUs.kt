package com.example.fashionmen.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.fashionmen.R

class DialogAboutUs: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val inflater = LayoutInflater.from(context).inflate(R.layout.dialog_about_us, null)

        val versionCodes = context!!.packageManager.getPackageInfo(context!!.packageName, 0).versionName

        val builder = AlertDialog.Builder(context)
        builder.setView(inflater)

        val textCode = inflater.findViewById<TextView>(R.id.version_code)
        textCode.text = versionCodes

        val closeButton = inflater.findViewById<Button>(R.id.close_btn)

        val alertDialog = builder.create()
        closeButton.setOnClickListener { alertDialog.dismiss() }

        return  alertDialog
    }
}