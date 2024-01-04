package com.example.gpacal

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {

    private lateinit var mainLayout: LinearLayout
    private var layoutList: Stack<LinearLayout> = Stack()
    private var hoursList: MutableList<EditText> = mutableListOf()
    private var gradeList: MutableList<Spinner> = mutableListOf()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainLayout = findViewById(R.id.mainLayout)

        val addCourse: Button = findViewById(R.id.addCourse)
        addCourse.setOnClickListener {
            addNewDetails()
        }

        val removeCourse: Button = findViewById(R.id.removeCourse)
        removeCourse.setOnClickListener {
            removeDetails()
        }

        val result: TextView = findViewById(R.id.result)
        val calculate: Button = findViewById(R.id.calculate)

        val firstCH: EditText = findViewById(R.id.firstCH)

        calculate.setOnClickListener {

                val ans: Double? = generateResult()

            if(ans!=null) {
                result.text = "Your GPA is: $ans"
            }
        }
        val optionsSpinner: Spinner = findViewById(R.id.optionsSpinner)
        hoursList.add(firstCH)
        gradeList.add(optionsSpinner)
        onSelectedItem(optionsSpinner)
    }
    private fun addNewDetails() {

        val addCourse: Button = findViewById(R.id.addCourse)
        (addCourse.parent as? ViewGroup)?.removeView(addCourse)

        val removeCourse: Button = findViewById(R.id.removeCourse)
        (removeCourse.parent as? ViewGroup)?.removeView(removeCourse)

        val calculate: Button = findViewById(R.id.calculate)
        (calculate.parent as? ViewGroup)?.removeView(calculate)

        val newLinearLayout = LinearLayout(this)
        newLinearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        newLinearLayout.orientation = LinearLayout.HORIZONTAL
            val newCHText = EditText(this)
            newCHText.hint = "Credit Hours"

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.weight = 3.0f

        newCHText.layoutParams = layoutParams

        val newGradeText = Spinner(this)

        val layoutParams2 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams2.weight = 6.0f

        newGradeText.layoutParams = layoutParams2

        onSelectedItem(newGradeText)

        hoursList.add(newCHText)
        gradeList.add(newGradeText)

        newLinearLayout.addView(newCHText)
        newLinearLayout.addView(newGradeText)

        layoutList.push(newLinearLayout)

        mainLayout.addView(newLinearLayout)
        mainLayout.addView(addCourse)
        mainLayout.addView(removeCourse)
        mainLayout.addView(calculate)

        mainLayout.requestLayout()
    }

    fun removeDetails() {
        mainLayout.removeView(layoutList.pop())
        gradeList.removeAt(gradeList.size-1)
        hoursList.removeAt(hoursList.size-1)
    }

    fun onSelectedItem(spinner: Spinner): String {

        val options = arrayOf("A+", "A", "B+", "B", "C", "C+", "C-", "F")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter
        var text: String = ""

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                text = options[position]
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Do nothing here
            }
        }
        return text
    }

    fun generateResult(): Double? {
        var sumOfQP: Double = 0.0
        var sumOfCH: Double = 0.0
        // since the size is going to be equal

        for(i in 0 until gradeList.size) {
            val selectedGrade: String = gradeList[i].selectedItem.toString()
            val points: Double = when(selectedGrade){
                "A+" -> 4.0
                "A" -> 3.5
                "B+" -> 3.0
                "B" -> 2.5
                "C+" -> 2.0
                "C" -> 1.5
                "C-" -> 1.0
                else -> 0.0
            }
            if(hoursList[i].text.toString().toIntOrNull() == null || hoursList[i].text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter all values properly!", Toast.LENGTH_SHORT).show()
                return null
            }
                val creditHours: Int = (hoursList[i].text.toString()).toInt()
                sumOfQP += points * creditHours
                sumOfCH += creditHours

        }

        val originalValue: Double = sumOfQP/sumOfCH
        val decimalPlaces: Int = 2

        val roundedValue: Double = BigDecimal(originalValue)
            .setScale(decimalPlaces, RoundingMode.HALF_UP)
            .toDouble()

        return roundedValue
    }
}

