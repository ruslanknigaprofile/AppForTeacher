package com.example.appforteacher.Domain.ViewModel

import androidx.navigation.NavController
import com.example.appforteacher.R

class navigationClass(val navController: NavController) {

    var from = "TaskListFragment"
    var to = ""

    fun replace(course: String){
        when(from){
            "TaskListFragment" ->{
                replaceFromTaskListFragment(course)
            }
            "CreateTestFragment" ->{
                replaceFromCreateTestFragment(course)
            }
            "CreateAnswerFragment" ->{
                replaceFromCreateAnswerFragment(course)
            }
            "AnswerTaskInfoFragment" ->{
                replaceFromAnswerTaskInfoFragment(course)
            }
            "GalleryFragment" ->{
                replaceFromAnswerGalleryFragment(course)
            }
        }
    }





    private fun replaceFromTaskListFragment(course: String){
        when(course){
            "CreateTestFragment" ->{
                navController.navigate(R.id.action_taskListFragment_to_createTaskFragment)
                from = course
            }
            "CreateAnswerFragment" ->{
                navController.navigate(R.id.action_taskListFragment_to_createAnswerFragment)
                from = course
            }
            "AnswerTaskInfoFragment" ->{
                navController.navigate(R.id.action_taskListFragment_to_taskInfoFragment)
                from = course
            }
        }
    }
    private fun replaceFromCreateTestFragment(course: String){
        when(course){
            "TaskListFragment" ->{
                navController.navigate(R.id.action_createTaskFragment_to_taskListFragment)
                from = course
            }
            "CreateAnswerFragment" ->{
                navController.navigate(R.id.action_createTaskFragment_to_createAnswerFragment)
                from = course
            }
        }
    }
    private fun replaceFromCreateAnswerFragment(course: String){
        when(course){
            "TaskListFragment" ->{
                navController.navigate(R.id.action_createAnswerFragment_to_taskListFragment)
                from = course
            }
            "CreateTestFragment" ->{
                navController.navigate(R.id.action_createAnswerFragment_to_createTaskFragment)
                from = course
            }
        }
    }
    private fun replaceFromAnswerTaskInfoFragment(course: String){
        when(course){
            "TaskListFragment" ->{
                navController.navigate(R.id.action_taskInfoFragment_to_taskListFragment)
                from = course
            }
            "GalleryFragment" ->{
                navController.navigate(R.id.action_taskInfoFragment_to_galleryFragment)
                from = course
            }
        }
    }

    private fun replaceFromAnswerGalleryFragment(course: String){
        when(course){
            "AnswerTaskInfoFragment" ->{
                navController.navigate(R.id.action_galleryFragment_to_taskInfoFragment)
                from = course
            }
            //!!!!1111
            "AnswerTestInfoFragment" ->{
                navController.navigate(R.id.action_galleryFragment_to_taskInfoFragment)
                from = course
            }
        }
    }
}