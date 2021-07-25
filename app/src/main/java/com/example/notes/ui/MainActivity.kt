package com.example.notes.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.notes.App
import com.example.notes.R
import com.example.notes.ui.list.ListOfNotesFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer)
        initDrawer()
        createFragment(AuthFragment.newInstance())
        app = application as App
    }

    private fun createFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun initDrawer() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.open, R.string.close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_list -> {
                    if (app.isAuthorized) {
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fragment_container, ListOfNotesFragment.newInstance()
                        ).commit()
                        drawer.closeDrawers()
                        true
                    }
                    false
                }
                R.id.menu_auth -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragment_container, AuthFragment.newInstance()
                    ).commit()
                    drawer.closeDrawers()
                    true
                }
                else -> false
            }
        }
    }
}