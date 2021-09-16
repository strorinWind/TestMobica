package ru.strorin.testmobica

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import ru.strorin.testmobica.network.Card
import ru.strorin.testmobica.network.Page
import ru.strorin.testmobica.network.RetrofitClient
import java.io.*
import java.util.concurrent.TimeoutException

@ExperimentalSerializationApi
class ListViewModel(
        private val retrofitApi: RetrofitClient,
        private val context: Context
): ViewModel() {

    companion object {
        private const val FILENAME = "cash_page.dat"
    }

    val data: LiveData<List<Card>>
        get() = _data
    private val _data: MutableLiveData<List<Card>> = MutableLiveData(emptyList())

    private val handler = CoroutineExceptionHandler { context, exception ->
        when(exception) {
            is TimeoutException -> println("given more time we schedule next network request to check if internet is here")
            else -> println("Caught $exception")
        }
    }

    init {
        loadFromFile()
        loadFromServer()
    }

    private fun loadFromServer() {
        viewModelScope.launch(handler) {
            withContext(Dispatchers.IO) {
                withTimeout(10000) {
                    startLoadingFromServer()
                }
            }
        }
    }

    private suspend fun startLoadingFromServer() {
        val page = retrofitApi.getList().page
        withContext(Dispatchers.Main) {
            _data.value = page.cards
        }
        saveToFile(page)
    }

    private fun saveToFile(page: Page) {
        val file = getCashFile()
        val encodeToString = Json.encodeToString(serializer(Page::class.java), page)
        Log.d("caught write", encodeToString)
        file.writeText(encodeToString)
    }

    private fun loadFromFile() {
        viewModelScope.launch(handler) {
            val page: Page
            withContext(Dispatchers.IO) {
                val file = getCashFile()
                val useLines = file.useLines { it.toList() }.first() //consider json as written single line
                page = Json.decodeFromString(serializer(Page::class.java), useLines) as Page
            }

            withContext(Dispatchers.Main) {
                _data.value = page.cards
            }
        }
    }

    private fun getCashFile(): File {
        val path = context.filesDir
        val letDirectory = File(path, "")
        letDirectory.mkdirs()
        return File(letDirectory, FILENAME)
    }
}