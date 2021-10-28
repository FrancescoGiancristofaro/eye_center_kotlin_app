package com.example.centro_oculistico

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.example.centro_oculistico.databinding.ActivityMainBinding
import com.example.centro_oculistico.ui.slideshow.UserAppointment
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity : AppCompatActivity(){

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var context: Context
    lateinit var pref :SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var sharedPreferenceChangeListener : SharedPreferences.OnSharedPreferenceChangeListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=applicationContext
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        pref=getSharedPreferences("userDataPreferences", MODE_PRIVATE)
        editor = pref.edit()
        if (pref.getString("cdf","default")!= "default" || pref.getString("doctorId","default")!= "default"){
            var myIcon = AppCompatResources.getDrawable(context, R.drawable.ic_user)
            binding.appBarMain.toolbar.overflowIcon=myIcon
        }else{
            var myIcon = AppCompatResources.getDrawable(context, R.drawable.enter)
            binding.appBarMain.toolbar.overflowIcon=myIcon
        }
         sharedPreferenceChangeListener =
            SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
                if (key == "cdf" || key == "doctorId") {
                    if (prefs.getString(key,"default")!= "default"){
                        var myIcon = AppCompatResources.getDrawable(context, R.drawable.ic_user)
                        binding.appBarMain.toolbar.overflowIcon=myIcon
                    }else{
                        var myIcon = AppCompatResources.getDrawable(context, R.drawable.enter)
                        binding.appBarMain.toolbar.overflowIcon=myIcon
                    }
                }


            }
        pref.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)

    }

    /*override fun onSharedPreferenceChanged(sharedPreferences : SharedPreferences, key : String){
        if (key=="cdf"){
            Toast.makeText(context,"shared client.", Toast.LENGTH_SHORT).show()
        }
        if (key=="doctorId"){
            Toast.makeText(context,"shared doc.",Toast.LENGTH_SHORT).show()
        }
    }*/
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    fun updateUserProfile(message:String){
        //Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
        binding.appBarMain.toolbar.menu.performIdentifierAction(R.id.userAppointment, 0);
    }

}