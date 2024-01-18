package com.example.kr_fedya.ui.registration

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
import com.example.kr_fedya.ui.login.Login

class Registration : AppCompatActivity() {
    private val dm: DatabaseManager = DatabaseManager(this)
    var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reg_screen)

        val toLogin: Button = findViewById<Button>(R.id.to_login_button)
        toLogin.setOnClickListener {
            val intent = Intent(this@Registration, Login::class.java)
            startActivity(intent)
        }

        val reg: Button = findViewById<Button>(R.id.reg_button)
        reg.setOnClickListener {
            if(registration()){
                val intent = Intent(this@Registration, MainActivity::class.java)
                intent.putExtra("isLogged", true)
                intent.putExtra("user", user)
                startActivity(intent)
            }
            else{
                failMessage(this)
            }
        }
    }

    fun registration() : Boolean{
        val name: String = findViewById<EditText>(R.id.name_reg).text.toString()
        val email: String = findViewById<EditText>(R.id.email_reg).text.toString()
        val password: String = findViewById<EditText>(R.id.password_reg).text.toString()

        if(name.isEmpty() || email.isEmpty() || password.isEmpty()) return false
        user = User(name, email, password)
        dm.openDb()
        dm.insertUser(name, email, password)
        dm.closeDb()
        return true
    }

    fun failMessage(context: Context) {
        context.toast("Ошибка регистрации")
    }
}