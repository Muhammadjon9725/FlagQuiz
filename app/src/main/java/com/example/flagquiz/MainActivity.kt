package com.example.flagquiz

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.children
import com.example.flagquiz.Models.Flag
import com.example.flagquiz.databinding.ActivityMainBinding
import java.util.Locale
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    lateinit var linearMatn:LinearLayout
    lateinit var linearbtn1:LinearLayout
    lateinit var linearbtn2:LinearLayout
    lateinit var imageView: ImageView

    lateinit var flagArrayList: ArrayList<Flag>
    var count = 0
    var countryname = ""
    lateinit var buttonArrayList: ArrayList<Button>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonArrayList = ArrayList()

        linearMatn = binding.lin1Txt
        linearbtn1 = binding.lin1Btn1
        linearbtn2 = binding.lin1Btn2
        imageView = binding.image
        obyektyaratish()
        btnJoylaCount()
    }


    private fun obyektyaratish() {
        flagArrayList = ArrayList()
        flagArrayList.add(Flag("uzbekistan",R.drawable.uzbekistan))
        flagArrayList.add(Flag("mexico",R.drawable.mexico))
        flagArrayList.add(Flag("turkiya",R.drawable.turkiye))
        flagArrayList.add(Flag("unitedshtates",R.drawable.unitedshtates))
        flagArrayList.add(Flag("luhemburg",R.drawable.luhemburg))

    }

    fun btnJoylaCount(){
        imageView.setImageResource(flagArrayList[count].image!!)
        linearMatn.removeAllViews()
        linearbtn1.removeAllViews()
        linearbtn2.removeAllViews()
        countryname = ""
        btnJoyla(flagArrayList[count].name)
    }

    private fun btnJoyla(countryName: String?) {
        val btnArray: ArrayList<Button> = randomBtn(countryName)
        for (i in 0..5) {
            linearbtn1.addView(btnArray[i])
        }
        for (i in 6..11) {
            linearbtn2.addView(btnArray[i])
        }
    }

    private fun randomBtn(countryName: String?): ArrayList<Button> {
        val array = ArrayList<Button>()
        val arrayText = ArrayList<String>()

        for (c in countryName!!) {
            arrayText.add(c.toString())
        }
        if (arrayText.size != 12) {
            val str = "ABCDEFGHIJKLMNOPQRSTUVXYZ"
            for (i in arrayText.size until 12) {
                val random = Random.nextInt(str.length)
                arrayText.add(str[random].toString())
            }
        }
        arrayText.shuffle()

        for (i in 0 until arrayText.size) {
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            )
            button.text = arrayText[i]
            button.setOnClickListener(this)
            array.add(button)
        }
        return array
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onClick(v: View?) {
        val button1 = v as Button
        if (buttonArrayList.contains(button1)) {
            linearMatn.removeView(button1)
            var hasC = false
            linearbtn1.children.forEach { button ->
                if ((button as Button).text.toString() == button1.text.toString()) {
                    button.visibility = View.VISIBLE
                    if (!hasC) {
                        countryname = countryname.substring(0, countryname.length - 1)
                    }
                }
            }
            linearbtn2.children.forEach { button ->
                if ((button as Button).text.toString() == button1.text.toString()) {
                    button.visibility = View.VISIBLE
                    if (!hasC) {
                        countryname = countryname.substring(0, countryname.length - 1)
                    }
                }
            }

        }else{
            button1.visibility = View.INVISIBLE
            countryname += button1.text.toString().toUpperCase()
            val button2 = Button(this)
            button2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
            button2.text = button1.text
            button2.setOnClickListener(this)
            buttonArrayList.add(button2)
            linearMatn.addView(button2)
            matntogri()
        }
    }


    private fun matntogri() {
        if (countryname == flagArrayList[count].name?.toUpperCase(Locale.ROOT)){
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
            if (count == flagArrayList.size-1){
                count = 0
            }else{
                count++
            }
            btnJoylaCount()
        }

        else{
            if (countryname.length == flagArrayList[count].name?.length){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                linearMatn.removeAllViews()
                linearbtn2.removeAllViews()
                linearbtn1.removeAllViews()
                btnJoyla(flagArrayList[count].name)
                countryname = ""
            }
        }
    }
}