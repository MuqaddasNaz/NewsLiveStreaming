package com.example.newslivestreaming

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FeedbackActivity : AppCompatActivity() {

    private lateinit var editTextFeedback: EditText
    private lateinit var buttonSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back)

        editTextFeedback = findViewById(R.id.et_your_name)
        editTextFeedback = findViewById(R.id.et_your_email)
        editTextFeedback = findViewById(R.id.et_your_contact_number)
        editTextFeedback = findViewById(R.id.et_your_feedback)
        buttonSubmit = findViewById(R.id.btn_send)

        buttonSubmit.setOnClickListener {
            val feedback = editTextFeedback.text.toString()
            if (feedback.isNotBlank()) {
                showToast("Thank you for your feedback!")

                editTextFeedback.text.clear()
            } else {
                showToast("Please enter your feedback.")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
