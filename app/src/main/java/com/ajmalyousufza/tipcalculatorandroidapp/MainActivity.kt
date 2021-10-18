package com.ajmalyousufza.tipcalculatorandroidapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import com.ajmalyousufza.tipcalculatorandroidapp.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding  // binding step 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // binding step 2
        setContentView(binding.root) // Binding step 3

        binding.calcualteButton.setOnClickListener{
            calculateTip()
        }
      
        binding.costOfServiceEdittext.setOnKeyListener{ view, keyCode, _ -> handleKeyEvent(view, keyCode)

        }
    }

    private fun calculateTip() {
       val stringInTextField = binding.costOfServiceEdittext.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if(cost==null){
            binding.tipResult.text=""
            return
        }
        val selectedId = binding.tipOptions.checkedRadioButtonId
        val tipPercentage = when(selectedId){
            R.id.eighty_percent_option -> 0.18
            R.id.twenty_percent_option -> 0.20
            else -> 0.15
        }
        var tip: Double = cost*tipPercentage
        val roundUp = binding.roundingUpSwitch.isChecked
        if(roundUp){
            tip = kotlin.math.ceil(tip)
        }
        val formatTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount,formatTip)
    }

    //Hiding keyboard after enter key pressed function
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}
