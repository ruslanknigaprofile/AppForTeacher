package com.example.appforteacher.Domain.ViewModel

import android.app.Application
import android.net.Uri
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.appforteacher.Presentation.View.Adapter.AnswerAdapter
import com.example.appforteacher.Presentation.View.Adapter.GalleryAdapter
import com.example.appforteacher.Presentation.View.Adapter.MoreInfoGallaryAdapter
import com.example.appforteacher.Presentation.View.Adapter.TaskListAdapter
import com.example.appforteacher.R
import com.example.appforteacher.Repository.DBReader
import com.example.appforteacher.Repository.DBWriter
import com.example.appforteacher.Task.Task
import java.lang.StringBuilder

class ViewModel(application: Application) : AndroidViewModel(application) {

    //Navigation
    private var navigation: navigationClass? = null
    var currentFragment = MutableLiveData<String>("TaskListFragment")

    private val dBWriter = DBWriter()
    private val dbReader = DBReader(this)
    var positionTaskDeleted = MutableLiveData<Int>()

    var positionMoreInfoTask = MutableLiveData<Int>()
    var galleryAdapterMFLiveData = MutableLiveData<MoreInfoGallaryAdapter>()
    var galleryImagesMF = MutableLiveData<ArrayList<Uri>>()
    var positionMoreInfoImagePick = MutableLiveData<Int>()

    var taskBody = MutableLiveData<String>("")
    val taskListAdapter = MutableLiveData<TaskListAdapter>()
    val tasksList = MutableLiveData<ArrayList<Task>>()
    var galleryAdapterLiveData = MutableLiveData<GalleryAdapter>()
    var answerAdapteLiveData = MutableLiveData<AnswerAdapter>()
    var switch = MutableLiveData<Boolean>()

    var listAnswersTest = MutableLiveData<ArrayList<String>>()
    var checkBooleanTest = MutableLiveData<ArrayList<String>>()
    var listAnswersAnswer = MutableLiveData<ArrayList<String>>()
    val listUris = MutableLiveData<ArrayList<String>>()

    init {
        listAnswersTest.value = arrayListOf()
        checkBooleanTest.value = arrayListOf()
        listAnswersAnswer.value = arrayListOf()
        listUris.value = arrayListOf()


        dbReader.getTasksList()
        tasksList.value = arrayListOf()

        galleryImagesMF.value = arrayListOf()
    }

    fun changeFragment(fragment: String){
        if (fragment == "Test") {
            switch.value = false
        } else if (fragment == "Answer"){
            switch.value = true
        }
        saveDataTest()
    }


    fun setGalleryAdapter(){
        galleryAdapterLiveData.value = GalleryAdapter(getApplication<Application>().baseContext, listUris.value!!, object : GalleryAdapter.PhotoListener{
            override fun onPhotoCLick(position: Int) {
                listUris.value?.removeAt(position)
                galleryAdapterLiveData.value?.notifyItemRemoved(position)
            }
        })
    }
    fun setMFGalleryAdapter(){
        galleryAdapterMFLiveData.value = MoreInfoGallaryAdapter(getApplication<Application>().baseContext,
        this)
    }

    fun setAnswerAdapter(){

        if (answerAdapteLiveData.value != null){
            answerAdapteLiveData.value = answerAdapteLiveData.value
        } else {
            answerAdapteLiveData.value = AnswerAdapter(listAnswersTest.value!!,
                checkBooleanTest.value!!,
                object : AnswerAdapter.RemoveButtonListener {
                    override fun removeItem(position: Int) {
                        removeAnswerInTest(position)
                    }
                })
        }
    }

    private fun removeAnswerInTest(position: Int){
        answerAdapteLiveData.value!!.removeAnswer(position)
        saveDataTest()
        answerAdapteLiveData.value!!.notifyItemRemoved(position)
    }
    fun saveDataTest(){
        if (answerAdapteLiveData.value != null) {
            listAnswersTest.value!!.clear()
            checkBooleanTest.value!!.clear()
            for (holder: AnswerAdapter.AnswerHolder in answerAdapteLiveData.value!!.holders) {
                listAnswersTest.value!!.add(holder.answer.text.toString())
                checkBooleanTest.value!!.add(holder.checkBox.isChecked.toString())
            }
        }
    }

    fun addAnswerTest(){
        saveDataTest()
        if(listAnswersTest.value!!.size <= 4) {
            answerAdapteLiveData.value!!.addAnswer()
            answerAdapteLiveData.value!!.notifyItemChanged(answerAdapteLiveData.value!!.itemCount - 1)
        }
    }

    fun listUrisAdd(string: String){
        if (listUris.value != null){
            listUris.value!!.add(string)
        }else{
            listUris.value = arrayListOf(string)
        }
    }

    fun setAnswer(string: String){
        if (listAnswersAnswer.value!!.size == 0){
            listAnswersAnswer.value!!.add(string)
        }else {
            listAnswersAnswer.value!!.set(0, string)
        }
    }
    fun setTaskBody(string: String){
        taskBody.value = string
    }

    fun addTaskInDB(){
        if (switch.value == true){
            if (taskBody.value != null && listUris.value != null && listAnswersAnswer.value != null ) {
                dBWriter.saveImage(listUris.value!!)
                val task = Task(
                    "",
                    taskBody.value!!,
                    "Answer",
                    listAnswersAnswer.value!!,
                    arrayListOf("true"),
                    listUris.value!!
                )
                dBWriter.saveTask(task)
            }
        }else {
            if (taskBody.value != null && listUris.value != null && listAnswersTest.value != null && checkBooleanTest.value != null) {
                saveDataTest()
                dBWriter.saveImage(listUris.value!!)
                val task = Task(
                    "",
                    taskBody.value!!,
                    "Test",
                    listAnswersTest.value!!,
                    checkBooleanTest.value!!,
                    listUris.value!!
                )
                dBWriter.saveTask(task)
            }
        }
        clearTask()
    }
    private fun clearTask(){
        taskBody.value = ""
        listAnswersAnswer.value?.clear()
        listAnswersTest.value?.clear()
        checkBooleanTest.value?.clear()
        listUris.value?.clear()
    }

    fun setTasksListAdapter(){
        if (tasksList.value != null) {
            taskListAdapter.value = TaskListAdapter(tasksList.value!!, object : TaskListAdapter.ClickImage{
                override fun clickImage(position: Int) {
                    positionTaskDeleted.value = position
                }
            },
            this)
        }
    }
    fun deleteItemTasksListByPath(){
        if (positionTaskDeleted.value != null){
            dbReader.deleteItem(tasksList.value!!.get(positionTaskDeleted.value!!).id)
        }
    }

    fun moreInfo(position: Int){
        positionMoreInfoTask.value = position
        dbReader.getImages(tasksList.value!!.get(position))
    }

    //Navigation
    fun setNavigation(navController: NavController){
        navigation = navigationClass(navController)
    }
    fun replace(course: String) {
        navigation?.replace(course)
        currentFragment.value = navigation?.from.toString()
    }


    override fun onCleared() {
        super.onCleared()
    }

}