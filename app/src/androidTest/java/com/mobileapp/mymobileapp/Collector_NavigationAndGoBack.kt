package com.mobileapp.mymobileapp


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.concurrent.thread

@LargeTest
@RunWith(AndroidJUnit4::class)
class Collector_NavigationAndGoBack {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun collector_NavigationAndGoBack() {
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_collectors), withContentDescription("Collectors"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        Thread.sleep(2000)
        bottomNavigationItemView.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.recyclerViewCollectors),
                childAtPosition(
                    withClassName(`is`("android.widget.LinearLayout")),
                    1
                )
            )
        )
        Thread.sleep(2000)
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val textView = onView(
            allOf(
                withText("Lista de coleccionistas"),
                withParent(
                    allOf(
                        withId(com.bumptech.glide.R.id.action_bar),
                        withParent(withId(com.bumptech.glide.R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        Thread.sleep(2000)
        textView.check(matches(withText("Lista de coleccionistas")))

        val textView2 = onView(
            allOf(
                withId(R.id.textViewCollectorName), withText("Manolo Bellon"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Manolo Bellon")))

        val textView3 = onView(
            allOf(
                withText("Favorite Performers"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Favorite Performers")))

        val textView4 = onView(
            allOf(
                withId(R.id.textViewPerformerName), withText("Rubén Blades Bellido de Luna"),
                withParent(withParent(withId(R.id.recyclerViewFavoritePerformers))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Rubén Blades Bellido de Luna")))

        val textView5 = onView(
            allOf(
                withId(R.id.textViewPerformerBirthDate), withText("1948"),
                withParent(withParent(withId(R.id.recyclerViewFavoritePerformers))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("1948")))

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(com.bumptech.glide.R.id.action_bar),
                        childAtPosition(
                            withId(com.bumptech.glide.R.id.action_bar_container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val textView6 = onView(
            allOf(
                withText("Lista de coleccionistas"),
                withParent(
                    allOf(
                        withId(com.bumptech.glide.R.id.action_bar),
                        withParent(withId(com.bumptech.glide.R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        Thread.sleep(2000)
        textView6.check(matches(withText("Lista de coleccionistas")))

        val textView7 = onView(
            allOf(
                withId(R.id.screenName), withText("Collectors"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("Collectors")))

        val textView8 = onView(
            allOf(
                withId(R.id.textViewCollectorName), withText("Manolo Bellon"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        textView8.check(matches(withText("Manolo Bellon")))

        val textView9 = onView(
            allOf(
                withId(R.id.textViewCollectorEmail), withText("manollo@caracol.com.co"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        textView9.check(matches(withText("manollo@caracol.com.co")))

        val appCompatImageButton2 = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(com.bumptech.glide.R.id.action_bar),
                        childAtPosition(
                            withId(com.bumptech.glide.R.id.action_bar_container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())
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
