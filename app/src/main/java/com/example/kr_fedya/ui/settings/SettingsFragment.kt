package com.example.kr_fedya.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.kr_fedya.Constants
import com.example.kr_fedya.R
import com.example.kr_fedya.databinding.FragmentSettingsBinding
import com.example.kr_fedya.dataclasses.User
import com.example.kr_fedya.db.DatabaseManager
import com.example.kr_fedya.toast
import com.google.gson.Gson

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private var dm: DatabaseManager? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        dm = DatabaseManager(this.requireActivity().applicationContext)
        val root: View = binding.root

        val sharedPrefs = this.activity?.getSharedPreferences(Constants.prefs, Context.MODE_PRIVATE)
        val isCar = sharedPrefs?.getBoolean("isCar", false) ?: false //Получаем isCar из префсов

        //Если машина есть, то загружаем картинку
        if(isCar){
            setImage()
        }

        //При клике на кнопку - обновляем
        binding.updateNameBtn.setOnClickListener {
            if(updateName(sharedPrefs))
                okMessage(requireContext())
            else{
                badMessage(requireContext())
            }
        }

        return root
    }

    fun okMessage(context: Context) {
        context.toast("Имя обновлено")
    }

    fun badMessage(context: Context) {
        context.toast("Пустая строка, имя не обновлено")
    }

    //С помощью библиотеки Glide отправляю запрос, получаю картинку как Bitmap, вставляю placeholder как чёрный экран,
    //пока загружается картинка, когда загрузится вставляю в imageView(carView)
    fun setImage(){
        val url = "https://wylsa.com/wp-content/uploads/2018/06/audi-a7.jpg"
        Glide.with(requireContext())
            .asBitmap()
            .load(url)
            .placeholder(ColorDrawable(Color.BLACK))
            .into(binding.carView)
    }

    fun updateName(sharedPrefs:SharedPreferences?) : Boolean{

        val name: String = binding.newNameTe.text.toString() //Получаем имя
        val user_json = sharedPrefs?.getString("user", "") //Получаем сериализованный объект
        val user = Gson().fromJson(user_json, User::class.java) //Десериализуем

        if(name.isEmpty()) return false //Проверяем поле ввода

        dm?.openDb() //Открываем бд и отправляем запрос на обновление
        dm?.updateName(name, user.email, user.password)
        dm?.closeDb()


        val user_json_n = Gson().toJson(User(name, user.email, user.password)) //Обновляем объект в префсах
        sharedPrefs?.edit()?.putString("user", user_json_n)?.apply()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}