package com.example.displayfirebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var edt_name:EditText
    lateinit var edt_email:EditText
    lateinit var btn_submit:Button
    lateinit var edt_age:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edt_name = findViewById(R.id.edtname)
        edt_email = findViewById(R.id.edtemail)
        edt_age = findViewById(R.id.edtage)
        btn_submit = findViewById(R.id.btnsubmit)


        btn_submit.setOnClickListener {

            var name = edt_name.text.toString().trim()
            var age = edt_age.text.toString().trim()
            var email = edt_email.text.toString().trim()

            var time_id = System.currentTimeMillis().toString()

            //progressbar
            var progress = ProgressDialog(this)
            progress.setTitle("Saving Data")
            progress.setMessage("Please wait")

            //validate
            if (name.isEmpty() || age.isEmpty() || email.isEmpty()){
                Toast.makeText(this, "Cannot sub,it an empty field", Toast.LENGTH_SHORT).show()
            }else{

                 var my_child = FirebaseDatabase.getInstance().reference.child("Names/" + time_id)
                var user_data = User(name, email, age, time_id)

                //show progress
                progress.show()

                my_child.setValue(user_data).addOnCompleteListener {

                    if (it.isSuccessful) {
                        Toast.makeText(this, "Data uploaded successfully", Toast.LENGTH_SHORT).show()

                        var gotoview = Intent(this, ViewUsers::class.java)
                        startActivity(gotoview)

                    }else{
                        Toast.makeText(this, "Failed to upload data", Toast.LENGTH_SHORT).show()
                    }

                }

            }

        }

    }
}