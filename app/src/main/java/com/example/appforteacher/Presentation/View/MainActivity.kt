package com.example.appforteacher.Presentation.View

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.appforteacher.Domain.ViewModel.ViewModel
import com.example.appforteacher.Domain.ViewModel.ViewModelFactory
import com.example.appforteacher.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.appbar.MaterialToolbar
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity : AppCompatActivity() {

    private lateinit var vm: ViewModel
    private lateinit var botomBar: ChipNavigationBar
    private lateinit var topBar: MaterialToolbar
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.fragment_place)
        vm = ViewModelProvider(this).get(ViewModel::class.java)
        vm.setNavigation(navController)

        init()
    }

    private fun init(){
        topBar = findViewById(R.id.topAppBar)
        topBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.addTopBar ->{
                    createSimpleDialog(
                        "Опубликовать задание?",
                        "Если хотите опубликовать созданное задание нажмите 'да'.",
                        { postTask() }
                    )
                    true
                }
                else -> {
                    false
                }
            }
        }
        topBar.menu.getItem(0).isVisible = false

        botomBar = findViewById(R.id.bNav)
        botomBar.setItemSelected(R.id.TasksMenu)
        botomBar.setOnItemSelectedListener {
            when(it){
                R.id.TasksMenu ->{
                    vm.replace("TaskListFragment")
                }
                R.id.AddTaskMenu ->{
                    if(vm.switch.value == true){
                        vm.replace("CreateAnswerFragment")
                    }else{
                        vm.replace("CreateTestFragment")
                    }
                }
            }
        }

        vm.currentFragment.observe(this){
            when(it){
                "TaskListFragment" ->{
                    botomBar.isVisible = true
                    topBar.menu.getItem(0).isVisible = false
                }
                "CreateAnswerFragment" ->{
                    topBar.menu.getItem(0).isVisible = true
                }
                "CreateTestFragment" ->{
                    topBar.menu.getItem(0).isVisible = true
                }
                "AnswerTaskInfoFragment" -> {
                    botomBar.isVisible = false
                }
            }
        }

        vm.positionTaskDeleted.observe(this){
            createSimpleDialog(
                "Удалить задание?",
                "Если хотите удалить выбранное задание нажмите 'да'.",
                { vm.deleteItemTasksListByPath() }
            )
        }
    }

    private fun createSimpleDialog(title: String, message: String, function: () -> Unit){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setNegativeButton("Нет") { dialogInterface, i ->
        }
        builder.setPositiveButton("Да") { dialogInterface, i ->
        function()
        }
        builder.show()
    }

    private fun postTask(){
        vm.addTaskInDB()
        botomBar.setItemSelected(R.id.TasksMenu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == ImagePicker.REQUEST_CODE && resultCode == RESULT_OK && data != null){
            vm.listUrisAdd(data.data.toString())
            vm.setGalleryAdapter()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        when(vm.currentFragment.value){
            "AnswerTaskInfoFragment" ->{
                vm.replace("TaskListFragment")
            }
            "CreateTestFragment" ->{
                vm.replace("TaskListFragment")
            }
            "CreateAnswerFragment" ->{
                vm.replace("TaskListFragment")
            }

            "GalleryFragment" ->{
                vm.replace("AnswerTaskInfoFragment")
            }
        }
    }
}