package ru.strorin.testmobica

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ru.strorin.testmobica.network.Card
import ru.strorin.testmobica.network.Page
import ru.strorin.testmobica.network.RetrofitClient
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

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
        val fos: FileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)
        val os = ObjectOutputStream(fos)
        os.writeObject(page)
        os.close()
        fos.close()
    }

    private fun loadFromFile() {
        viewModelScope.launch(handler) {
            val page: Page
            withContext(Dispatchers.IO) {
                val fis: FileInputStream = context.openFileInput(FILENAME)
                val stream = ObjectInputStream(fis)
                page = stream.readObject() as Page
                stream.close()
                fis.close()
            }

            withContext(Dispatchers.Main) {
                _data.value = page.cards
            }
        }
    }
}