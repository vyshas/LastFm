package com.example.testlastfm.ui.searchAlbums

import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.testlastfm.AppExecutors
import com.example.testlastfm.R
import com.example.testlastfm.model.Album
import kotlinx.android.synthetic.main.item_search_album.view.*

class SearchAlbumAdapter(
    appExecutors: AppExecutors, private val albumClickCallback: ((Album) -> Unit)?
) : ListAdapter<Album, SearchAlbumAdapter.ViewHolder>(
    AsyncDifferConfig.Builder<Album>(DiffCallback())
        .setBackgroundThreadExecutor(appExecutors.diskIO())
        .build()
) {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Album
            albumClickCallback?.invoke(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAlbumAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_album, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchAlbumAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.albumNameView.text = item.name
        holder.artistNameView.text = item.artist
        //TODO IMAGE

        with(holder.view) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val artistNameView: TextView = view.artist_name
        val albumNameView: TextView = view.album_name
    }

    class DiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem?.albumId == newItem?.albumId
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }
}