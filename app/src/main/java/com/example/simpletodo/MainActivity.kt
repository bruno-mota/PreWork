package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var tasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val onLongClickListener= object: TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                tasks.removeAt(position)
                adapter.notifyDataSetChanged()
                saveItems()

            }

        }
        loadItems()

        val rvTask = findViewById<View>(R.id.recyclerView) as RecyclerView
        adapter= TaskItemAdapter(tasks,onLongClickListener)
        rvTask.adapter = adapter
        rvTask.layoutManager=LinearLayoutManager(this)

        val addTaskField = findViewById<EditText>(R.id.addTaskField)
        findViewById<Button>(R.id.button).setOnClickListener{

            val taskText = addTaskField.text.toString()
            tasks.add(taskText)
            adapter.notifyItemInserted(tasks.size-1)
            addTaskField.setText("")
            saveItems()
        }
    }
    fun getDataFile():File{
        return File(filesDir,"taskData.txt")
    }
    fun loadItems(){
        try{
            tasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())

        }catch(ioException:IOException){
            ioException.printStackTrace()
        }
    }
    fun saveItems(){
        try{
            FileUtils.writeLines(getDataFile(),tasks)

        }catch(ioException:IOException){
            ioException.printStackTrace()
        }
    }
}