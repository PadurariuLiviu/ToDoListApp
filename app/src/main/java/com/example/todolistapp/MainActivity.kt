package com.example.todolistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.todolistapp.Database.TaskViewModel
import com.example.todolistapp.Fragments.TaskFragment
import com.example.todolistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var myFragment: TaskFragment
    lateinit var viewModel: TaskViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this,R.id.activity_main_nav_host_fragment)
        val bundle = Bundle()
        setupWithNavController(binding.bottomNavigationView,navController)

        myFragment = TaskFragment()
        supportFragmentManager.beginTransaction().add(R.id.activity_main_nav_host_fragment, myFragment , "MyFragment").commit()

        binding.apply {

            toggle = ActionBarDrawerToggle(this@MainActivity,drawerLayout,R.string.open,R.string.close)
            drawerLayout.addDrawerListener(toggle)

            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Tasks"

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()


            navViewMenu.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.personalItem->{
                        bundle.putString("filter","personal")
                        navController.navigate(R.id.taskFragment,bundle)
                        Toast.makeText(this@MainActivity,"Personal",Toast.LENGTH_SHORT).show()
                        drawerLayout.closeDrawer(GravityCompat.START)

                    }
                    R.id.workItem->{
                        bundle.putString("filter","work")
                        navController.navigate(R.id.taskFragment,bundle)
                        Toast.makeText(this@MainActivity,"Work",Toast.LENGTH_SHORT).show()
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    R.id.schoolItem->{
                        bundle.putString("filter","school")
                        navController.navigate(R.id.taskFragment,bundle)
                        Toast.makeText(this@MainActivity,"School",Toast.LENGTH_SHORT).show()

                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                }
                true
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            true
        }
        /*
        return when(item.itemId) {
            R.id.addItem -> {
                //Toast.makeText(this@MainActivity, "AddItem", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }

         */
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navig_menu_custom,menu)
        return true
    }
}