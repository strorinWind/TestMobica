package ru.strorin.testmobica

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import ru.strorin.testmobica.network.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ViewModelTest {

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @ExperimentalSerializationApi
    @Test
    fun serverRequestTest() {
        val retrofit = mock(RetrofitClient::class.java)
        val ctx = mock(Context::class.java)

        val viewModel = ListViewModel(retrofit, ctx)
        runBlocking {
            verify(retrofit).getList()
        }
    }
}