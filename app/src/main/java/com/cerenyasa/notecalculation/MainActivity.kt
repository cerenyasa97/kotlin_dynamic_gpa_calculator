package com.cerenyasa.notecalculation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.view.get
import com.cerenyasa.notecalculation.databinding.ActivityMainBinding
import com.shashank.sony.fancytoastlib.FancyToast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val LECTURES = arrayOf("Math", "Literature", "Physics", "Algorithm", "History", "Electronics", "Computer Programming")
    private var allLecturesInfo: ArrayList<Lectures> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, LECTURES)

        binding.etLectureName.setAdapter(adapter)

        if (binding.rootLayout.childCount == 0) binding.calculateButton.visibility = View.INVISIBLE
        else binding.calculateButton.visibility = View.VISIBLE

        binding.addButton.setOnClickListener {
            if (!binding.etLectureName.text.isNullOrEmpty()) {
                var inflater = LayoutInflater.from(this)
                var newLectureView = inflater.inflate(R.layout.new_lecture_layout, null)

                // Get static data from user input
                var noteName = binding.etLectureName.text
                var noteCredit = binding.creditSpinner.selectedItem.toString()
                var noteLetter = binding.letterSpinner.selectedItem.toString()

                newLectureView.findViewById<AutoCompleteTextView>(R.id.etNewLectureName).setAdapter(adapter)
                newLectureView.findViewById<EditText>(R.id.etNewLectureName).text = noteName
                newLectureView.findViewById<Spinner>(R.id.newLetterSpinner).setSelection(getSpinnerIndexFromValue(binding.letterSpinner, noteLetter))
                newLectureView.findViewById<Spinner>(R.id.newCreditSpinner).setSelection(getSpinnerIndexFromValue(binding.creditSpinner, noteCredit))

                newLectureView.findViewById<Button>(R.id.deleteButton).setOnClickListener {
                    binding.rootLayout.removeView(newLectureView)

                    if (binding.rootLayout.childCount == 0) binding.calculateButton.visibility = View.INVISIBLE
                    else binding.calculateButton.visibility = View.VISIBLE
                }

                binding.rootLayout.addView(newLectureView)

                binding.calculateButton.visibility = View.VISIBLE

                reset()
            } else {
                FancyToast.makeText(this, "Enter lecture name", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show()
            }
        }
    }

    private fun reset() {
        binding.etLectureName.setText("")
        binding.letterSpinner.setSelection(0)
        binding.creditSpinner.setSelection(0)
    }

    fun getSpinnerIndexFromValue(spinner: Spinner, searchValue: String): Int {
        var index = 0
        for (i in 0..spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(searchValue)) {
                index = i
                break
            }
        }
        return index
    }

    fun calculateAverage(view: View) {
        var sumNote = .0
        var sumCredit = .0

        allLecturesInfo.clear()
        addLectureInInfoArray()

        for (thatLecture in allLecturesInfo){
            sumNote += convertLetterToNote(thatLecture.lectureLetter) * thatLecture.lectureCredit.toDouble()
            sumCredit += thatLecture.lectureCredit.toDouble()
        }

        FancyToast.makeText(this, "AVERAGE: " + (sumNote/sumCredit), FancyToast.LENGTH_LONG, FancyToast.DEFAULT, false).show()
        allLecturesInfo.clear()
    }

    fun addLectureInInfoArray(){
        for (i in 0..(binding.rootLayout.childCount - 1)) {
            var oneLine = binding.rootLayout.get(i)

            var dummyLecture = Lectures(
                oneLine.findViewById<AutoCompleteTextView>(R.id.etNewLectureName).text.toString(),
                (oneLine.findViewById<Spinner>(R.id.newCreditSpinner).selectedItemPosition + 1).toString(),
                oneLine.findViewById<Spinner>(R.id.newLetterSpinner).selectedItem.toString()
            )

            allLecturesInfo.add(dummyLecture)
        }
    }

    fun convertLetterToNote(str: String) : Double{
        var value = 0.0
        when(str){
            "AA" -> value = 4.0
            "BA" -> value = 3.5
            "BB" -> value = 3.0
            "CB" -> value = 2.5
            "CC" -> value = 2.0
            "DC" -> value = 1.5
            "DD" -> value = 1.0
            "FF" -> value = 0.0
        }
        return value
    }
}