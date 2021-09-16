package ru.strorin.testmobica

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import ru.strorin.testmobica.network.RetrofitObject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ViewModelAndroidTest {
    @ExperimentalSerializationApi
    @Test
    fun checkServerConnection() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ru.strorin.testmobica", appContext.packageName)

        val viewModel = ListViewModel(RetrofitObject.retrofitClient, appContext)
        Thread.sleep(3000) //time to finish request
        runBlocking {
            assertTrue(viewModel.data.value?.size ?: 0 > 0)
        }
    }
}