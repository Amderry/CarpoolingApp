package com.example.kr_fedya.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.kr_fedya.MainActivity
import com.example.kr_fedya.R
import com.example.kr_fedya.dataclasses.User
import com.example.kr_fedya.db.DatabaseManager
import com.example.kr_fedya.toast
import com.example.kr_fedya.ui.registration.Registration

class Login : AppCompatActivity() {
    private val dm: DatabaseManager = DatabaseManager(this)
    var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        val toReg: Button = findViewById<Button>(R.id.to_reg_button)
        toReg.setOnClickListener {
            val intent = Intent(this@Login, Registration::class.java)
            startActivity(intent)
        }

        val enter: Button = findViewById<Button>(R.id.entry_button)
        enter.setOnClickListener {

            if(login()){
                val intent = Intent(this@Login, MainActivity::class.java)
                intent.putExtra("isLogged", true)
                intent.putExtra("user", user)
                startActivity(intent)
            }
            else{
                failMessage(this)
            }
        }
    }

    fun login() : Boolean{

        val email: String = findViewById<EditText>(R.id.login_email).text.toString()
        val password: String = findViewById<EditText>(R.id.password_login).text.toString()

        dm.openDb()
        user = dm.getUser(email, password)
        dm.closeDb()

        return user != null
    }

    fun failMessage(context: Context) {
        context.toast("Ошибка авторизации")
    }
}