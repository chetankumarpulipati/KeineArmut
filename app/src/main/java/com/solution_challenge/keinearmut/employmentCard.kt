package com.solution_challenge.keinearmut

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import org.json.JSONObject

class employmentCard: AppCompatActivity() {
    private lateinit var textField: MaterialAutoCompleteTextView
    private lateinit var age: EditText
    private lateinit var hoursperweek:EditText
    private lateinit var income:EditText
    private lateinit var predict:Button
    private lateinit var result:TextView



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.employment_card)

        val items = listOf("Formal", "Self", "Unemployed")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items)
        val field_selection = findViewById<AutoCompleteTextView>(R.id.auto_complete_text_view)
        field_selection.setAdapter(adapter)
        field_selection.setOnItemClickListener { parent, view, position, id ->
            val workclass = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "Selected: $workclass", Toast.LENGTH_SHORT).show()
        }



        val items1 = listOf("dropout","HS dropout","HS-grad","Intermediate Education","Bachelors/Doctoral")
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items1)
        val field_selection1 = findViewById<AutoCompleteTextView>(R.id.auto_complete_text_view1)
        field_selection1.setAdapter(adapter1)

        field_selection1.setOnItemClickListener { parent, view, position, id ->
            val education = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "Selected: $education", Toast.LENGTH_SHORT).show()

        }
        val items2 = listOf("Never Married","Currently Married","Previously Married")
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items2)
        val field_selection2 = findViewById<AutoCompleteTextView>(R.id.auto_complete_text_view2)
        field_selection2.setAdapter(adapter2)

        field_selection2.setOnItemClickListener { parent, view, position, id ->
            val maritalstatus = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "Selected: $maritalstatus", Toast.LENGTH_SHORT).show()

        }
        age=findViewById<EditText>(R.id.age)
        hoursperweek=findViewById(R.id.hoursperweek)
        income=findViewById(R.id.income)
        predict=findViewById<Button>(R.id.predictor)
        result=findViewById(R.id.display)




        val radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)
        radioGroupGender.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButtonMale -> {
                    Toast.makeText(this, "Male selected", Toast.LENGTH_SHORT).show()
                }

                R.id.radioButtonFemale -> {
                    Toast.makeText(this, "Female selected", Toast.LENGTH_SHORT).show()
                }
            }

        }
        predict.setOnClickListener{
            val url = "https://test-ajrtk4rmwa-ue.a.run.app/predict"
            val workclass = field_selection.text.toString()
            val education = field_selection1.text.toString()
            val maritalstatus = field_selection2.text.toString()
            val age = age.text.toString()
            val hoursPerWeek = hoursperweek.text.toString()
            val income = income.text.toString()
            val gender = when (radioGroupGender.checkedRadioButtonId) {
                R.id.radioButtonMale -> "Male"
                R.id.radioButtonFemale -> "Female"
                else ->""
            }

            val jsonObject = JSONObject()
            jsonObject.put("age", age)
            jsonObject.put("workclass", workclass)
            jsonObject.put("education", education)
            jsonObject.put("marital-status", maritalstatus)
            jsonObject.put("gender", gender)
            jsonObject.put("hours-per-Week", hoursPerWeek)
            jsonObject.put("income", income)

            val requestQueue = Volley.newRequestQueue(this)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, url, jsonObject,
                Response.Listener { response ->
                    val prediction = response.getString("poverty-status")
                    if (prediction =="0"){
                        result.text = "Below Poverty"
                    }
                    else if(prediction=="1") {
                        result.text="Above Poverty"
                    }

                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                })

            requestQueue.add(jsonObjectRequest)
        }
        }
    }




