package com.example.custombottomnavigation

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.custombottomnavigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
//    private var toast: Toast? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.container) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Apply bottom inset to the custom navigation view
        ViewCompat.setOnApplyWindowInsetsListener(binding.customNav) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Add bottom padding equal to the system navigation bar height
            v.setPadding(
                v.paddingLeft,
                v.paddingTop,
                v.paddingRight,
                v.paddingBottom + systemBars.bottom
            )
            insets
        }
        binding.customNav.onItemSelected = { index ->
//            toast?.cancel()
//            toast = Toast.makeText(this, "Selected $index", Toast.LENGTH_SHORT)
//            toast?.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
//            toast?.show()

            // swap fragments or handle navigation here
            // For example, if you wanted to show different fragments:
            // val fragmentManager = supportFragmentManager
            // val transaction = fragmentManager.beginTransaction()
            // when (index) {
            //     0 -> transaction.replace(R.id.container, FragmentOne())
            //     1 -> transaction.replace(R.id.container, FragmentTwo())
            //     2 -> transaction.replace(R.id.container, FragmentThree())
            //     3 -> transaction.replace(R.id.container, FragmentFour())
            // }
            // transaction.commit()
        }
    }
}