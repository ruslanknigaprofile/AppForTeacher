package com.example.appforteacher.Presentation.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appforteacher.R

class GalleryAdapter(val context: Context,val images: ArrayList<String>,val photoListener: PhotoListener): RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView =  itemView.findViewById(R.id.image)
        var removeImage: ImageView = itemView.findViewById(R.id.removeImage)
    }

    interface PhotoListener{
        fun onPhotoCLick(position: Int)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.galery_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val image: String = images.get(position)
        Glide.with(context).load(image).into(holder.imageView)

        holder.removeImage.setOnClickListener {
            photoListener.onPhotoCLick(holder.position)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}