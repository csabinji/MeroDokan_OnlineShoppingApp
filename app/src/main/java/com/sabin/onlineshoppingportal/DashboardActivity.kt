package com.sabin.onlineshoppingportal

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sabin.onlineshoppingportal.adapter.ProductAdapter
import com.sabin.onlineshoppingportal.adapter.ViewPageAdapter
import com.sabin.onlineshoppingportal.fragment.AccountFragment
import com.sabin.onlineshoppingportal.fragment.AddProductFragment
import com.sabin.onlineshoppingportal.fragment.CartFragment
import com.sabin.onlineshoppingportal.fragment.HomeFragment
import com.sabin.onlineshoppingportal.model.Product

class DashboardActivity : AppCompatActivity() {

    private val permissions = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    private lateinit var lstTitle : ArrayList<String>
    private lateinit var lstFragments : ArrayList<Fragment>
    private lateinit var tabs : TabLayout
    private lateinit var viewPager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        if (!hasPermission()) {
            requestPermission()
        }

        tabs = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.viewPager)

        populateList()

        val adapter = ViewPageAdapter(lstFragments,supportFragmentManager,lifecycle)
        viewPager.adapter = adapter
        TabLayoutMediator(tabs,viewPager) {tab, position ->
            tab.text = lstTitle[position]

            if(lstTitle[position] == "Home"){
                tab.setIcon(R.drawable.home)
            }else if(lstTitle[position] == "Add Product"){
                tab.setIcon(R.drawable.addproduct)
            }else{
                tab.setIcon(R.drawable.account)
            }
        }.attach()
    }
    private fun populateList(){
        lstTitle = ArrayList<String>()
        lstTitle.add("Home")
        lstTitle.add("Add Product")
        lstTitle.add("Account")

        lstFragments = ArrayList<Fragment>()
        lstFragments.add(HomeFragment())
        lstFragments.add(AddProductFragment())
        lstFragments.add(AccountFragment())

    }
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
                this@DashboardActivity,
                permissions, 1434
        )
    }

    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                            this,
                            permission
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
            }
        }
        return hasPermission
    }
}