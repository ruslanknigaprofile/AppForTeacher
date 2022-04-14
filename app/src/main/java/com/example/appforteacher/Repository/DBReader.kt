package com.example.appforteacher.Repository


import android.net.Uri
import com.example.appforteacher.Domain.ViewModel.ViewModel
import com.example.appforteacher.Task.Task
import com.google.firebase.storage.FileDownloadTask


class DBReader(val vm: ViewModel) {

    private val connectorDB = ConnectorDB()

    fun getTasksList(){
        connectorDB.readFromDB(vm)
    }

    fun deleteItem(path: String){
        connectorDB.deleteFromDB(path)
    }

    fun getImages(task: Task){
        connectorDB.getImages(task, vm)
    }
}