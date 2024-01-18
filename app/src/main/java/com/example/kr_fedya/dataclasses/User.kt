package com.example.kr_fedya.dataclasses

import java.io.Serializable

data class User(val name: String, val email:String, val password:String) : Serializable {
    fun println(){
        println("User: ${this.name} - ${this.email} - ${this.password}")
    }
}