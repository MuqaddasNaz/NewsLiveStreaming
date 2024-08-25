package com.example.newslivestreaming

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.Models.UserFeedback
import com.google.firebase.database.FirebaseDatabase

class HelpActivity : AppCompatActivity() {

    private var context: Context = this@HelpActivity

    private lateinit var editTextName: EditText
    private lateinit var editTextContactNumber: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextFeedback: EditText
    private lateinit var buttonSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        Functions.setTransparentStatusBar(context)

        val imageViewBack = findViewById<ImageView>(R.id.icon_back)
        imageViewBack.setOnClickListener {
            onBackPressed()
        }

        editTextName = findViewById(R.id.et_your_name)
        editTextContactNumber = findViewById(R.id.et_your_contact_number)
        editTextEmail = findViewById(R.id.et_your_email)
        editTextFeedback = findViewById(R.id.et_your_feedback)
        buttonSubmit = findViewById(R.id.btn_send)

        buttonSubmit.setOnClickListener {
            val name = editTextName.text.toString()
            val contactNumber = editTextContactNumber.text.toString()
            val email = editTextEmail.text.toString()
            val feedback = editTextFeedback.text.toString()


            if (name.isNotEmpty() && contactNumber.isNotEmpty() && email.isNotEmpty() && feedback.isNotEmpty()) {
                val database = FirebaseDatabase.getInstance()
                val ref = database.getReference("Feedback")
                val feedbackId = ref.push().key


                if (feedbackId != null) {
                    val userFeedback = UserFeedback(feedbackId, name, contactNumber, email, feedback)

                    ref.child(feedbackId).setValue(userFeedback)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show()
                            editTextName.setText("")
                            editTextContactNumber.setText("")
                            editTextEmail.setText("")
                            editTextFeedback.setText("")
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to submit feedback", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
