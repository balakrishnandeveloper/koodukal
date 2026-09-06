package com.android.mykoodugalapplication.commonUtils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.android.mykoodugalapplication.R

object ApiCustomLoader {

    private var isShowing = false
    var customDialog: CustomDialog? = null

    fun show(context: Context?) {
        if (!isShowing) {
            isShowing = true
            try {
                customDialog = CustomDialog(context)
                customDialog?.setCancelable(false)
                customDialog?.setCanceledOnTouchOutside(false)
                customDialog?.setContentView(R.layout.customloader_layout)
                customDialog?.findViewById<ImageView>(R.id.loading_icon)?.startAnimation(
                    AnimationUtils.loadAnimation(context, R.anim.custom_loader))
                val window: Window? =
                    customDialog?.window
                window?.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                )
                customDialog?.show()
            } catch (e: Exception) {
                println("jira apicustom loaded $e")
                e.printStackTrace()
            }
        }
    }

    fun dismiss() {
        if (isShowing) {
            isShowing = false
            try {
                customDialog?.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

class CustomDialog(context: Context?) : Dialog(context!!) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}