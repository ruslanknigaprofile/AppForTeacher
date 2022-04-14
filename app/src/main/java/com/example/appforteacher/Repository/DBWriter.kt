package com.example.appforteacher.Repository

import com.example.appforteacher.Task.Task


class DBWriter {

    private val connectorDB = ConnectorDB()

    fun saveImage(listUris: ArrayList<String>){
        connectorDB.writeInStorage(listUris)
    }

    fun saveTask(task: Task){
        connectorDB.writeInDB(task)
    }
}