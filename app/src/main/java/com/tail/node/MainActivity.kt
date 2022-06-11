package com.tail.node

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tail.node.Utils.LocationService
import com.tail.node.Utils.UserSessionManager
import com.tail.node.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    private lateinit var binding : ActivityMainBinding
    private var session: UserSessionManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        session = UserSessionManager(applicationContext)

        if (session!!.isUserLoggedIn) {
            ContextCompat.startForegroundService(this, Intent(this, LocationService::class.java))
            LocationService().mLocation
            val intent = Intent(this, Welcome::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {
            ContextCompat.startForegroundService(this, Intent(this, LocationService::class.java))
            LocationService().mLocation
            val userName = binding.userName.text.trim().toString()
            val phoneNumber = binding.phoneNumber.text.trim().toString()
            if (userName.isEmpty()){
                Toast.makeText(this, "Enter Your Name", Toast.LENGTH_LONG).show()
            }else{
                if (phoneNumber.isEmpty()){
                    Toast.makeText(this, "Enter Your Mobile Number", Toast.LENGTH_LONG).show()
                }else{
                    session!!.createUserLoginSession(userName, phoneNumber)
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
                    val i = Intent(this, Welcome::class.java)
                    startActivity(i)
                    finish()
                }
            }
        }
    }

}