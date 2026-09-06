package com.android.mykoodugalapplication.commonUtils

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.android.mykoodugalapplication.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Common {

    fun showLogoutDialog(activity: Activity,message: String,title:String) {

        val dialogView = LayoutInflater.from(activity)
            .inflate(R.layout.dialog_confirm, null)

        val dialog = AlertDialog.Builder(activity)
            .setView(dialogView)
            .create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(false)
        val btnCancel = dialogView.findViewById<TextView>(R.id.btnCancel)
        val btnConfirm = dialogView.findViewById<TextView>(R.id.btnConfirm)
        val txtMessage = dialogView.findViewById<TextView>(R.id.txtMessage)
        val txtTitle = dialogView.findViewById<TextView>(R.id.txtTitle)
        txtMessage.text = message
        txtTitle.text=title
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            dialog.dismiss()
            activity.finish()   // logout
        }

        dialog.show()
    }

    fun showDatePicker(
        context: Context,
        onDateSelected: (String) -> Unit
    ) {

        val calendar = Calendar.getInstance()

        val dialog = DatePickerDialog(
            context,
            R.style.MyDatePickerTheme,
            { _, year, month, day ->

                calendar.set(year, month, day)

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                onDateSelected(sdf.format(calendar.time))

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        dialog.show()
    }

    fun showMessage(
        context: Context,
        title: String,
        message: String
    ) {

        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}