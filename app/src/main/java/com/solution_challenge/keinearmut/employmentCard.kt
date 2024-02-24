package com.solution_challenge.keinearmut

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class employmentCard: AppCompatActivity() {
    private lateinit var textField: MaterialAutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.employment_card)

        val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items)
        val field_selection = findViewById<AutoCompleteTextView>(R.id.auto_complete_text_view)
        field_selection.setAdapter(adapter)

        field_selection.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "Selected: $selectedItem", Toast.LENGTH_LONG).show()
        }

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
    }
}
