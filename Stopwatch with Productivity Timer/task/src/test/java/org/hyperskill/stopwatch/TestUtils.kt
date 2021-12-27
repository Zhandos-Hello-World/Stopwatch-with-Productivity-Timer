package org.hyperskill.stopwatch

import android.app.Activity
import android.view.View
import org.junit.Assert

// Version 1.4
object TestUtils {

    inline fun <reified T> findViewByString(idString: String, activity: Activity): T {
        val id = activity.resources.getIdentifier(idString, "id", activity.packageName)
        val view: View? = activity.findViewById(id)

        val idNotFoundMessage = "View with id \"$idString\" was not found"
        val wrongClassMessage = "View with id \"$idString\" is not from expected class. " +
                "Expected ${T::class.java.simpleName} found ${view?.javaClass?.simpleName}"

        Assert.assertNotNull(idNotFoundMessage, view)
        Assert.assertTrue(wrongClassMessage, view is T)

        return view as T
    }
}