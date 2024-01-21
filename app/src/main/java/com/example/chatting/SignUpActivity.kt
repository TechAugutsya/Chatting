package com.example.chatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var edtEmail : EditText
    private lateinit var edtName : EditText
    private lateinit var edtPassword : EditText
    private lateinit var bttnSignUp : Button
    private  lateinit var auth: FirebaseAuth
    private   var firebaseUser: FirebaseUser? = null
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        edtName = findViewById(R.id.edt_name)
        bttnSignUp = findViewById(R.id.btn_sign_up)
        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser

        bttnSignUp.setOnClickListener{
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val name = edtName.text.toString()

            signUp(name,email,password);
        }
    }

    private fun signUp(name:String,email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful){
                    addUserToDatabase(name,email,auth.currentUser?.uid!!)

                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this,"Some Error occurred",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        val user:FirebaseUser? = auth.currentUser
        val userId:String = user!!.uid

        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("Users").child(uid).setValue(User(name,email,uid))

    }
}