package com.musnadil.challengechapter5.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.musnadil.challengechapter5.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RegistLogin {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashScreenActivity::class.java)

    @Test
    fun registLogin() {
        Thread.sleep(6000)
        val materialTextView = onView(
            allOf(
                withId(R.id.btn_register), withText("Create new one"),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.et_username),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("asd"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.et_email),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("asd"), closeSoftKeyboard())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.et_passowrd),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("asd"), closeSoftKeyboard())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.et_confirm_passowrd),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("asd"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.btn_signup), withText("Sign Up"),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.et_passowrd),
                isDisplayed()
            )
        )
        textInputEditText5.perform(replaceText("asd"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.btn_login), withText("Sign In"),
                isDisplayed()
            )
        )
        materialButton2.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
