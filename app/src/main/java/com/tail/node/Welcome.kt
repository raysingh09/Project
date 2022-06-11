package com.tail.node

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.tail.node.databinding.ActivityWelcomeBinding
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class Welcome : AppCompatActivity() {
    private lateinit var binding : ActivityWelcomeBinding
    var SAMPLE_DB_NAME = "TailNode.db"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, 10000)


        val sharedPreferences: SharedPreferences = this.getSharedPreferences("LatLong", MODE_PRIVATE)
        val lat = sharedPreferences.getString("lat", "")
        val long = sharedPreferences.getString("long","")


        lat.let {
            binding.latitude.text = "Lattitude :- $lat"
        }
        long.let {
            binding.longitude.text = "Longitude :- $long"
        }

        binding.exportDatabase.setOnClickListener {
            val latlong = binding.longitude.text.toString() + binding.latitude.text.toString()
            val FILE_NAME = "latlong.txt";
            var fos: FileOutputStream? = null
            try {
                fos = openFileOutput(FILE_NAME, MODE_PRIVATE)
                fos.write(latlong.toByteArray())
                Toast.makeText(applicationContext, "Your File Saved to $filesDir/$FILE_NAME", Toast.LENGTH_LONG).show()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (fos != null) {
                    try {
                        fos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }        }

    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }



}