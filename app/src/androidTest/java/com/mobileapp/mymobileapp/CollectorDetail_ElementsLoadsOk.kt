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

@LargeTest
@RunWith(AndroidJUnit4::class)
class CollectorDetail_ElementsLoadsOk {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun collectorDetail_ElementsLoadsOk() {
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
        bottomNavigationItemView.perform(click())
        Thread.sleep(2000)

        val bottomNavigationItemView2 = onView(
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
        bottomNavigationItemView2.perform(click())
        Thread.sleep(2000)

        val recyclerView = onView(
            allOf(
                withId(R.id.recyclerViewCollectors),
                childAtPosition(
                    withClassName(`is`("android.widget.LinearLayout")),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))
        Thread.sleep(2000)

        val recyclerView2 = onView(
            allOf(
                withId(R.id.recyclerViewFavoritePerformers),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        recyclerView2.check(matches(isDisplayed()))

        val linearLayout = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.recyclerViewCollectorAlbums),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        linearLayout.check(matches(isDisplayed()))

        val linearLayout2 = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.recyclerViewCollectorAlbums),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        linearLayout2.check(matches(isDisplayed()))

        val imageView = onView(
            allOf(
                withId(R.id.imageViewPerformer), withContentDescription("Performer Image"),
                withParent(withParent(withId(R.id.recyclerViewFavoritePerformers))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val imageView2 = onView(
            allOf(
                withId(R.id.imageViewAlbum), withContentDescription("Album Image"),
                withParent(withParent(withId(R.id.recyclerViewCollectorAlbums))),
                isDisplayed()
            )
        )
        imageView2.check(matches(isDisplayed()))
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
