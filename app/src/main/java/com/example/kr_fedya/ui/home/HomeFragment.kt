package com.example.kr_fedya.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kr_fedya.Constants
import com.example.kr_fedya.dataclasses.User
import com.example.kr_fedya.databinding.FragmentHomeBinding
import com.example.kr_fedya.db.DatabaseManager
import com.example.kr_fedya.toast
import com.example.kr_fedya.ui.login.Login
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var dm: DatabaseManager? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        dm = DatabaseManager(this.requireActivity().applicationContext)
        val root: View = binding.root

        val sharedPrefs = this.activity?.getSharedPreferences(Constants.prefs, Context.MODE_PRIVATE)
        val isLogged = sharedPrefs?.getBoolean("isLogged", false) ?: false

        val exit : Button = binding.exitButton

        //Кнопка выход
        exit.setOnClickListener {
            val intent = Intent(this.activity, Login::class.java)
            sharedPrefs?.edit()?.clear()?.apply()
            startActivity(intent)
        }

        //Кнопка регистрации или входа
        val regEntry : Button = binding.regEntryButton
        regEntry.setOnClickListener {
            val intent = Intent(this.activity, Login::class.java)
            startActivity(intent)
        }

        val isCar = sharedPrefs?.getBoolean("isCar", false) ?: false

        //Вставляем имя машины, если есть машины
        if(isCar){
            binding.carName.text = "Машина: Ауди семёрка"
        }
        else {
            binding.carName.text = "Машина: Нет"
        }

        //Получчаем объект из префсов
        val user_json = sharedPrefs?.getString("user", "")
        val user = Gson().fromJson(user_json, User::class.java)

        //Если залогинен, то вставляем поля
        if(isLogged){
            val loginName: TextView = binding.loginName
            val loginEmail: TextView = binding.loginEmail
            println("Name: ${user?.name}")
            loginName.text = "Имя: ${user?.name}"
            loginEmail.text = "Почта: ${user?.email}"
            println("Зареган: $isLogged")
            regEntry.visibility = View.GONE
        }
        //Иначе скрываем данные поля
        else{
            exit.visibility = View.GONE
            binding.carName.visibility = View.GONE
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}