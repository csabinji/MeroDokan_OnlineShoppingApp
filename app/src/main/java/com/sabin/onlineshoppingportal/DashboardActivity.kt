package com.sabin.onlineshoppingportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sabin.onlineshoppingportal.adapter.ViewPageAdapter
import com.sabin.onlineshoppingportal.fragment.AccountFragment
import com.sabin.onlineshoppingportal.fragment.CartFragment
import com.sabin.onlineshoppingportal.fragment.HomeFragment

class DashboardActivity : AppCompatActivity() {

    private lateinit var lstTitle : ArrayList<String>
    private lateinit var lstFragments : ArrayList<Fragment>
    private lateinit var tabs : TabLayout
    private lateinit var viewPager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        tabs = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.viewPager)

        populateList()

        val adapter = ViewPageAdapter(lstFragments,supportFragmentManager,lifecycle)
        viewPager.adapter = adapter
        TabLayoutMediator(tabs,viewPager) {tab, position ->
            tab.text = lstTitle[position]

            if(lstTitle[position] == "Home"){
                tab.setIcon(R.drawable.home)
            }else if(lstTitle[position] == "Cart"){
                tab.setIcon(R.drawable.cart)
            }else{
                tab.setIcon(R.drawable.account)
            }
        }.attach()
    }
    private fun populateList(){
        lstTitle = ArrayList<String>()
        lstTitle.add("Home")
        lstTitle.add("Cart")
        lstTitle.add("Account")

        lstFragments = ArrayList<Fragment>()
        lstFragments.add(HomeFragment())
        lstFragments.add(CartFragment())
        lstFragments.add(AccountFragment())

    }
}