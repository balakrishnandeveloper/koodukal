package com.android.mykoodugalapplication.activity


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.mykoodugalapplication.R
import com.android.mykoodugalapplication.commonUtils.Common
import com.android.mykoodugalapplication.databinding.ActivityMainBinding
import com.android.mykoodugalapplication.fragment.HomeFragment
import com.android.mykoodugalapplication.fragment.NestInstalledFragment
import com.android.mykoodugalapplication.fragment.ProfileFragment
import com.android.mykoodugalapplication.fragment.SearchFragment
import com.android.mykoodugalapplication.viwemodel.LoginViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private var doubleBackToExitPressedOnce = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        // Drawer Toggle (Hamburger icon)
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.white)
        intiView()
        loadFragment(HomeFragment())
    }


    private fun intiView() {

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                val scale = 1 - (slideOffset * 0.2f)   // main screen shrink
                val endScale = 0.8f + (slideOffset * 0.2f) // drawer zoom

                binding.fragmentContainer.apply {
                    scaleX = scale
                    scaleY = scale
                    translationX = drawerView.width * slideOffset
                }

                drawerView.apply {
                    scaleX = endScale
                    scaleY = endScale
                    alpha = 0.6f + (slideOffset * 0.4f)
                }
            }

            override fun onDrawerOpened(drawerView: View) {}

            override fun onDrawerClosed(drawerView: View) {}

            override fun onDrawerStateChanged(newState: Int) {}
        })

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home ->    loadFragment(HomeFragment())
                R.id.nav_search ->  loadFragment(SearchFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
                R.id.nav_nest -> loadFragment(NestInstalledFragment())
            }
            true
        }

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_projects -> {
                    val intent = Intent(this,ProjectsActivity::class.java)
                    startActivity(intent)
                }
                R.id.menu_recognitions -> {
                    val intent = Intent(this,RecognitionsActivity::class.java)
                    startActivity(intent)
                }
                R.id.menu_media_partners -> {
                    val intent = Intent(this,MediaActivity::class.java)
                    startActivity(intent)
                }
                R.id.menu_about -> {
                    val intent = Intent(this,AboutActivity::class.java)
                    startActivity(intent)
                }
                R.id.menu_feedback -> {
                    val intent = Intent(this, FeedbackActivity::class.java)
                    startActivity(intent)
                }
                R.id.menu_logout->{
                    Common.showLogoutDialog(this,"Are you sure you want to logout?","Logout")
                }
                R.id.menu_delete->{
                    Common.showLogoutDialog(this,"This action cannot be undone. Do you want to delete your account?","Delete Account")
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right)
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    fun setBottomNavSelection(itemId: Int) {
        binding.bottomNavigation.selectedItemId = itemId
    }

    override fun onBackPressed() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            return
        }

        // 👉 If not on Home → go to Home
        if (binding.bottomNavigation.selectedItemId != R.id.nav_home) {
            binding.bottomNavigation.selectedItemId = R.id.nav_home
        }

        if (doubleBackToExitPressedOnce) {
            finishAffinity()    // Exit the app
            return
        }
        doubleBackToExitPressedOnce = true

        Toast.makeText(
            this,
            "Press back again to exit",
            Toast.LENGTH_SHORT
        ).show()

        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }


}