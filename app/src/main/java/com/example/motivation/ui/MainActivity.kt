package com.example.motivation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.motivation.infra.MotivationConstants
import com.example.motivation.R
import com.example.motivation.repository.Mock
import com.example.motivation.infra.SecurityPreferences
import com.example.motivation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var categoryId = MotivationConstants.FILTER.ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Esconde a barra de navegação
        supportActionBar?.hide()
        handleFilter(R.id.im_all)
        handleNextPhrase()

        // Eventos
        setListeners()

    }

    override fun onResume() {
        super.onResume()

        handleUserName()
    }

    private fun setListeners() {
        binding.buttonNewPhrase.setOnClickListener(this)
        binding.imAll.setOnClickListener(this)
        binding.imHappy.setOnClickListener(this)
        binding.imSunny.setOnClickListener(this)
        binding.textUserName.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val id: Int = v.id

        val listId = listOf(
            R.id.im_all,
            R.id.im_happy,
            R.id.im_sunny
        )

        if(id in listId){
            handleFilter(id)
        } else if (v.id == R.id.button_new_phrase){
            handleNextPhrase()
        } else if (v.id == R.id.text_user_name){
            startActivity(Intent(this, UserActivity::class.java))
        }
    }

    private fun handleUserName(){
        val name = SecurityPreferences(this).getString(MotivationConstants.KEY.USER_NAME)
        binding.textUserName.text = "Olá, $name!"
    }

    private fun handleFilter(id: Int){
        binding.imAll.setColorFilter(ContextCompat.getColor(this, R.color.purple))
        binding.imHappy.setColorFilter(ContextCompat.getColor(this, R.color.purple))
        binding.imSunny.setColorFilter(ContextCompat.getColor(this, R.color.purple))

        when (id) {
            R.id.im_all -> {
                binding.imAll.setColorFilter(ContextCompat.getColor(this, R.color.white))
                categoryId = MotivationConstants.FILTER.ALL
            }
            R.id.im_happy -> {
                binding.imHappy.setColorFilter(ContextCompat.getColor(this, R.color.white))
                categoryId = MotivationConstants.FILTER.HAPPY
            }
            R.id.im_sunny -> {
                binding.imSunny.setColorFilter(ContextCompat.getColor(this, R.color.white))
                categoryId = MotivationConstants.FILTER.SUNNY
            }
        }
    }

    private fun handleNextPhrase(){
        binding.textFraseParaUser.text = Mock().getPhrase(categoryId)
    }
}