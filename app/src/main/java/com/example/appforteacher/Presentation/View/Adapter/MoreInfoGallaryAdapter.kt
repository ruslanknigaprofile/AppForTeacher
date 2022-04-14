package com.example.appforteacher.Presentation.View.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appforteacher.Domain.ViewModel.ViewModel
import com.example.appforteacher.Presentation.View.MainActivity
import com.example.appforteacher.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.FileDownloadTask

class MoreInfoGallaryAdapter(val context: Context, val vm: ViewModel): RecyclerView.Adapter<MoreInfoGallaryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView =  itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.galery_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(vm.galleryImagesMF.value!!.get(position)).into(holder.imageView)

        holder.imageView.setOnClickListener {
            vm.replace("GalleryFragment")
            vm.positionMoreInfoImagePick.value = holder.position
        }
    }

    override fun getItemCount(): Int {
        return vm.galleryImagesMF.value!!.size
    }
}