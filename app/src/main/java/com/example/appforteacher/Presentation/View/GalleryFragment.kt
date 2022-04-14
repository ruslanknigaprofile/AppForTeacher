package com.example.appforteacher.Presentation.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appforteacher.Domain.ViewModel.ViewModel
import com.example.appforteacher.Domain.ViewModel.ViewModelFactory
import com.example.appforteacher.R


class GalleryFragment : Fragment() {

    private lateinit var vm: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)

        init(view)

        return view
    }

    private fun init(view: View){
        Glide.with(requireActivity())
            .load(vm.galleryImagesMF.value!!.get(vm.positionMoreInfoImagePick.value!!))
            .into(view.findViewById(R.id.provide_image))
    }
}