package com.example.kr_fedya.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.kr_fedya.toast

class CarDialog(val action: () -> Unit ) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Ауди семёрка")
                .setMessage("Эта машина свободна, занять?")
                .setPositiveButton("Да") {
                    dialog, id ->
                    action()
                    okMessage(requireContext())
                    dialog.cancel();
                }
                .setNegativeButton("Нет") {

                    dialog, id -> dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun okMessage(context: Context) {
        context.toast("Вы заняли машину")
    }
}