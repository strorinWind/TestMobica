package ru.strorin.testmobica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.strorin.testmobica.ui.ListAdapter

class MainActivity : AppCompatActivity() {

    private val viewModel: ListViewModel by viewModels { ListViewModelFactory(this) }
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = ListAdapter()
        recycler.adapter = adapter

        viewModel.data.observe(this, {adapter.setList(it)})
    }
}