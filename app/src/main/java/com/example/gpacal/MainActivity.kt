package com.example.gpacal

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView

class MainActivity : AppCompatActivity() {

    private lateinit var mainLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainLayout = findViewById(R.id.mainLayout)

        val addCourse: Button = findViewById(R.id.addCourse)
        addCourse.setOnClickListener {
            addNewDetails()
        }
    }
    private fun addNewDetails() {

        val addCourse: Button = findViewById(R.id.addCourse)
        (addCourse.parent as? ViewGroup)?.removeView(addCourse)

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

        val newGradeText = EditText(this)
        newGradeText.hint = "Grade"

        val layoutParams2 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams2.weight = 3.0f

        newGradeText.layoutParams = layoutParams2

        newLinearLayout.addView(newCHText)
        newLinearLayout.addView(newGradeText)

        mainLayout.addView(newLinearLayout)
        mainLayout.addView(addCourse)


    }

}