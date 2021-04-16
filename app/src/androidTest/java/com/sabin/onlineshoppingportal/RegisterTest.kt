package com.sabin.onlineshoppingportal

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class RegisterTest {
    @get:Rule
    val testRule = ActivityScenarioRule(SignUpActivity::class.java)

    @Test
    fun checkRegister(){
        onView(withId(R.id.etxtFname))
            .perform(typeText("Unish Bhattarai"))

        onView(withId(R.id.etxtUser))
            .perform(typeText("unish"))

        onView(withId(R.id.etxtEmail))
            .perform(typeText("unish@gmail.com"))

        onView(withId(R.id.spnUser))
            .perform()

        onView(withId(R.id.etxtPass))
            .perform(typeText("unish"))

        onView(withId(R.id.etxtRepass))
            .perform(typeText("unish"))

        onView(withId(R.id.btnSignup))
            .perform(click())

        Thread.sleep(2000)

        onView(withId(R.id.etxtUsername))
            .check(matches(isDisplayed()))
    }
}