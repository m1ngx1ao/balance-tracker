package app.balancetracker.controller.reuse

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import app.balancetracker.R

class CustomToast(
    private val applicationContext: Context,
    private val layoutInflater: LayoutInflater
) {
    fun show(message: String,
        backgroundColor: Int = R.color.c64_lightGrey,
        borderColor: Int = R.color.c64_grey
    ) {
        val layout = layoutInflater.inflate(R.layout.toast, null)
        layout.findViewById<View>(R.id.toastLinearLayout)
            .setBackgroundColor(applicationContext.getColor(borderColor))
        val toastTextView = layout.findViewById<TextView>(R.id.toastTextView)
        toastTextView.text = message
        toastTextView.setBackgroundColor(applicationContext.getColor(backgroundColor))
        with (Toast(applicationContext)) {
            duration = Toast.LENGTH_SHORT
            view = layout
            show()
        }
    }
}