package com.example.kr_fedya

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kr_fedya.databinding.ActivityMainBinding
import com.example.kr_fedya.dataclasses.User
import com.google.gson.Gson

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var isLogged: Boolean = false
    private var user: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPrefs = getSharedPreferences(Constants.prefs, Context.MODE_PRIVATE)
        isLogged = sharedPrefs.getBoolean("isLogged", false)
        val user_json = sharedPrefs.getString("user", "")
        user = Gson().fromJson(user_json, User::class.java)

        if(!isLogged){
            isLogged = intent.getBooleanExtra("isLogged", false)
            user = intent.getSerializableExtra("user") as User?
            sharedPrefs.edit().putBoolean("isLogged", isLogged).apply()
            val user_json_n = Gson().toJson(user)
            sharedPrefs.edit().putString("user", user_json_n).apply()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }
}