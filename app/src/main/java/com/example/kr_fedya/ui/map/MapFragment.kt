package com.example.kr_fedya.ui.map

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.kr_fedya.Constants
import com.example.kr_fedya.dataclasses.User
import com.example.kr_fedya.databinding.FragmentMapBinding
import com.example.kr_fedya.db.DatabaseManager
import com.example.kr_fedya.toast
import com.example.kr_fedya.ui.CarDialog
import com.example.kr_fedya.ui.login.Login
import com.google.gson.Gson
import androidx.fragment.app.DialogFragment;

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private var dm: DatabaseManager? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dm = DatabaseManager(this.requireActivity().applicationContext)
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val car_btn = binding.carBtn

        car_btn.setOnClickListener {
            val carDialog = CarDialog { setIsImage() }
            carDialog.show(requireActivity().supportFragmentManager, "Dialog")
        }

        return root
    }

    fun setIsImage(){
        val sharedPrefs = this.activity?.getSharedPreferences(Constants.prefs, Context.MODE_PRIVATE)
        sharedPrefs?.edit()?.putBoolean("isCar", true)?.apply()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}