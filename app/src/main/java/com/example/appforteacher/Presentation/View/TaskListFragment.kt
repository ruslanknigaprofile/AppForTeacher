package com.example.appforteacher.Presentation.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforteacher.Domain.ViewModel.ViewModel
import com.example.appforteacher.Domain.ViewModel.ViewModelFactory
import com.example.appforteacher.R


class TaskListFragment : Fragment() {

    private lateinit var vm: ViewModel

    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        init(view)

        return view

    }

    private fun init(view: View){
        recyclerView = view.findViewById(R.id.taskList)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        vm.taskListAdapter.observe(requireActivity()){
            recyclerView!!.adapter = it
        }
        vm.tasksList.observe(requireActivity()){
            vm.setTasksListAdapter()
        }
    }
}