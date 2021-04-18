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

class ProductUpdateTest {
    @get : Rule
    val testRule = ActivityScenarioRule(UpdateProductActivity::class.java)

    @Test
    fun checkProductUpdate(){
        ServiceBuilder.token="Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiI2MDc4Mjc1MTkyNjc1ZTJmZjBlZThiZTciLCJpYXQiOjE2MTg2NjgxODV9.bJSRXUPWmrG-i89EFwz-5CLlAlvK-P8XKgS8INiUQUk"

        onView(ViewMatchers.withId(R.id.imgProduct))
                .perform(ViewActions.typeText("noimg.jpg"))

        onView(ViewMatchers.withId(R.id.etxtPname))
                .perform(ViewActions.typeText("Mobile phone"))

        onView(ViewMatchers.withId(R.id.etxtPrice))
                .perform(ViewActions.typeText("85420"))

        onView(ViewMatchers.withId(R.id.etxtPdec))
                .perform(ViewActions.typeText("Made in China"))

        closeSoftKeyboard()

        onView(ViewMatchers.withId(R.id.btnUpdate))
                .perform(ViewActions.click())

        Thread.sleep(2000)

        onView(ViewMatchers.withId(R.id.banner))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}