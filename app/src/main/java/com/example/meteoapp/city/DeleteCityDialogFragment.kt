package com.example.meteoapp.city

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.meteoapp.R

class DeleteCityDialogFragment: DialogFragment() {

    private lateinit var cityName: String

    interface DeleteCityDialogListener {
        fun onDialogPostiveClick()
        fun onDialogNegativeClick()
    }

    var listener: DeleteCityDialogListener? = null

    companion object {

        val EXTRA_CITY_NAME = "com.example.meteoapp.city.EXTRA_CITY_NAME"

        fun newInstance(cityName: String): DeleteCityDialogFragment{
            val fragment = DeleteCityDialogFragment()

            fragment.arguments = Bundle().apply {
                putString(EXTRA_CITY_NAME, cityName)
            }

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityName = arguments?.getString(EXTRA_CITY_NAME).toString()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.context!!)

        builder.setTitle(getString(R.string.deletecity_title, cityName))
            .setPositiveButton(getString(R.string.deletecity_positive)) { _, _ ->
                listener?.onDialogPostiveClick()
            }
            .setNegativeButton(getString(R.string.deletecity_negative)) { dialog, _ ->
                dialog.cancel()
                listener?.onDialogNegativeClick()
            }

        return builder.create()
    }

}