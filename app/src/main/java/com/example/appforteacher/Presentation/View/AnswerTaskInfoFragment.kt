package com.example.appforteacher.Presentation.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforteacher.Domain.ViewModel.ViewModel
import com.example.appforteacher.Domain.ViewModel.ViewModelFactory
import com.example.appforteacher.R


class AnswerTaskInfoFragment : Fragment() {

    private lateinit var vm: ViewModel

    var gallerySize: ImageView? = null
    var taskBody: TextView? = null
    var answer: TextView? = null
    var addImage: TextView? = null
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_answer_task_info, container, false)

        init(view)

        return view
    }

    private fun init(view: View){
        taskBody = view.findViewById(R.id.taskBodyText)
        if (vm.tasksList.value!!.get(vm.positionMoreInfoTask.value!!).bodyTask != ""){
            taskBody!!.text = "Задание: "+ vm.tasksList.value!!.get(vm.positionMoreInfoTask.value!!).bodyTask
        }
        else{
            taskBody!!.text = "Задание: <не указано>."
        }


        answer = view.findViewById(R.id.rightAnswerText)
        if (vm.tasksList.value!!.get(vm.positionMoreInfoTask.value!!).listAnswers.size > 0){
            answer!!.text ="Верный ответ: "+ vm.tasksList.value!!.get(vm.positionMoreInfoTask.value!!).listAnswers.get(0)
        }
        else{
            answer!!.text ="Верный ответ: <не указано>."
        }

        addImage = view.findViewById(R.id.itemCountMF)
        recyclerView = view.findViewById(R.id.recyclerViewGalleryTask)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(requireContext(),3)

        vm.galleryAdapterMFLiveData.observe(requireActivity()) {
            recyclerView?.adapter = it
            if (it.itemCount > 0){
                addImage!!.text = "Прикреплённые изображения."
            }
        }
        vm.galleryImagesMF.observe(requireActivity()){
            vm.setMFGalleryAdapter()
        }
    }



}