package com.kakadurf.randomgifs.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kakadurf.randomgifs.R
import com.kakadurf.randomgifs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navController =
            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment)
                .navController
        binding
            .bottomNavigationView.setupWithNavController(navController)
    }
}
