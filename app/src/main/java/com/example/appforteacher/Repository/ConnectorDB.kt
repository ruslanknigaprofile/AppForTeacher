package com.example.appforteacher.Repository

import android.net.Uri
import androidx.core.net.toUri
import com.example.appforteacher.Domain.ViewModel.ViewModel
import com.example.appforteacher.Task.Task
import com.google.firebase.database.*
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class ConnectorDB {

    val mDataBase: DatabaseReference = FirebaseDatabase.getInstance().getReference("Task")
    val REF_STORAGE_ROOT: StorageReference = FirebaseStorage.getInstance().reference

    fun writeInStorage(listUris: ArrayList<String>) {
        for (i in listUris) {
            REF_STORAGE_ROOT.child("folder_task_image")
                .child(i).putFile(i.toUri())
        }
    }

    fun writeInDB(task: Task){
        val push = mDataBase.push()
        task.id = push.key.toString()
        push.setValue(task)
    }

    fun getImages(task: Task, vm: ViewModel){
        val arrImg: ArrayList<Uri> = arrayListOf()

        vm.galleryImagesMF.value?.clear()
        for (i in task.listImageUrl){
            REF_STORAGE_ROOT.child("folder_task_image").child(i).downloadUrl.addOnCompleteListener {
                arrImg.add(it.result)
                vm.galleryImagesMF.value = arrImg
            }
        }
    }

    fun readFromDB(vm: ViewModel) {
        val tasksList: ArrayList<Task> = arrayListOf()
        mDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tasksList.clear()
                for (ds in snapshot.children) {
                    tasksList.add(ds.getValue(Task::class.java) as Task)
                }
                vm.tasksList.value = tasksList
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun deleteFromDB(path: String){
        mDataBase.child(path).removeValue()
    }
}

