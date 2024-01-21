package com.example.chatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var edtEmail :EditText
    private lateinit var edtPassword :EditText
    private lateinit var bttnLogin :Button
    private lateinit var bttnSignUp :Button
    private  lateinit var auth: FirebaseAuth
    private   var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        bttnLogin = findViewById(R.id.btn_login)
        bttnSignUp = findViewById(R.id.btn_sign_up)
        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser

        bttnSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        
        bttnLogin.setOnClickListener{
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            
            login(email,password);
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful){

                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                }
                else
                {
                    Toast.makeText(applicationContext,  "email or password  invalid", Toast.LENGTH_SHORT).show()

                }
            }
    }
}