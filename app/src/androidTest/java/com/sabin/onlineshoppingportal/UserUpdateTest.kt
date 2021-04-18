package com.sabin.onlineshoppingportal

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.sabin.onlineshoppingportal.api.ServiceBuilder
import org.junit.Rule
import org.junit.Test

class UserUpdateTest {
    @get : Rule
    val testRule = ActivityScenarioRule(UpdateProfileActivity::class.java)

    @Test
    fun checkUpdateUser(){
        ServiceBuilder.token="Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiI2MDc4Mjc1MTkyNjc1ZTJmZjBlZThiZTciLCJpYXQiOjE2MTg2NjgxODV9.bJSRXUPWmrG-i89EFwz-5CLlAlvK-P8XKgS8INiUQUk"

        onView(ViewMatchers.withId(R.id.imgProduct))
                .perform()

        onView(ViewMatchers.withId(R.id.etxtUFname))
                .perform(ViewActions.typeText("Sabin Chapagain"))

        closeSoftKeyboard()

        onView(ViewMatchers.withId(R.id.etxtUUsername))
                .perform(ViewActions.typeText("unish"))

        closeSoftKeyboard()

        onView(ViewMatchers.withId(R.id.etxtUEmail))
                .perform(ViewActions.typeText("unishB@gmail.com"))

        closeSoftKeyboard()

        onView(ViewMatchers.withId(R.id.etxtUPhone))
                .perform(ViewActions.typeText("98145789545"))

        closeSoftKeyboard()

        onView(ViewMatchers.withId(R.id.etxtStreet))
                .perform(ViewActions.typeText("Banehsowr"))

        closeSoftKeyboard()

        onView(ViewMatchers.withId(R.id.etxtCity))
                .perform(ViewActions.typeText("Kathmandu"))

        closeSoftKeyboard()

        onView(ViewMatchers.withId(R.id.etxtState))
                .perform(ViewActions.typeText("State 3"))

        closeSoftKeyboard()

        onView(ViewMatchers.withId(R.id.etxtZip))
                .perform(ViewActions.typeText("58475"))

        closeSoftKeyboard()

        onView(ViewMatchers.withId(R.id.btnUpdate))
                .perform(ViewActions.click())

        Thread.sleep(2000)

        onView(ViewMatchers.withId(R.id.banner))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}