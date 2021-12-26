package org.hyperskill.stopwatch

import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

// Version 1.4
@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {

    private val activityController = Robolectric.buildActivity(MainActivity::class.java)

    private val activity: Activity by lazy {
        activityController.setup().get()
    }
    private val startButton: Button by lazy {
        findByViewByString("startButton", activity)
    }
    private val resetButton: Button by lazy {
        findByViewByString("resetButton", activity)
    }
    private val textView: TextView by lazy {
        findByViewByString("textView", activity)
    }


    @Test
    fun testShouldCheckStartButtonExist() {
        startButton
    }

    @Test
    fun testShouldCheckResetButtonExist() {
        resetButton
    }

    @Test
    fun testShouldCheckTextViewExist() {
        textView
    }

    @Test
    fun testShouldCheckStartButtonText() {
        val message = "in button property \"text\""
        assertEquals(message, "Start", startButton.text)
    }

    @Test
    fun testShouldCheckResetButtonText() {
        val message = "in button property \"text\""
        assertEquals(message, "Reset", resetButton.text)
    }

    private inline fun <reified T> findByViewByString(idString: String, activity: Activity): T {
        val id = activity.resources.getIdentifier(idString, "id", activity.packageName)
        val view: View? = activity.findViewById(id)

        val idNotFoundMessage = "View with id \"$idString\" was not found"
        val wrongClassMessage = "View with id \"$idString\" is not from expected class. " +
                "Expected ${T::class.java.simpleName} found ${view?.javaClass?.simpleName}"

        assertNotNull(idNotFoundMessage, view)
        assertTrue(wrongClassMessage, view is T)

        return view as T
    }

}