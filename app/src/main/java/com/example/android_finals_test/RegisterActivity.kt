package com.example.android_finals_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val userCollectionName = "user-sample"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerAndLoginButton = findViewById<Button>(R.id.registerAndLoginButton)
        auth = Firebase.auth
        val db = Firebase.firestore
        val emailET = findViewById<EditText>(R.id.registerEmailET)
        val passwordET = findViewById<EditText>(R.id.registerPasswordET)
        val displayNameET = findViewById<EditText>(R.id.registerDisplayNameET)

        registerAndLoginButton.setOnClickListener {
            val email = emailET.text.toString()
            val password = passwordET.text.toString()
            val displayName = displayNameET.text.toString()

            if (email != "" && password != "" && displayName != ""){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            val city = hashMapOf(
                                "userId" to userId,
                                "displayName" to displayName,
                            )

                            db.collection(userCollectionName).document()
                                .set(city)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        baseContext,
                                        "Registered Successfully",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        baseContext,
                                        "Registration Failed",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }

                            finish()
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Registration Failed",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }

        }
    }
}