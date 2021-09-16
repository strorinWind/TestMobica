package ru.strorin.testmobica

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.serialization.ExperimentalSerializationApi
import ru.strorin.testmobica.network.RetrofitObject
import java.lang.IllegalArgumentException

class ListViewModelFactory(
        context: Context
): ViewModelProvider.Factory {

    private val applicationContext = context.applicationContext

    @ExperimentalSerializationApi
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            ListViewModel::class.java -> {
                val model = ListViewModel(
                        RetrofitObject.retrofitClient,
                        applicationContext
                        )
                model as T
            }
            else -> throw IllegalArgumentException("viewmodel type is not supported")
        }

}