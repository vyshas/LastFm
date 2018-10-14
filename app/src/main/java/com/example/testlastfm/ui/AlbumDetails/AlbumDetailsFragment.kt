package com.example.testlastfm.ui.AlbumDetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.example.testlastfm.R
import com.example.testlastfm.dependencyinjection.Injectable
import com.example.testlastfm.model.Album
import kotlinx.android.synthetic.main.fragment_album_details.*

import javax.inject.Inject

class AlbumDetailsFragment : Fragment(), Injectable {

    private var albumDetailsViewModel: AlbumDetailsViewModel? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var layoutView: View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_album_details, container, false)

        layoutView = view

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        albumDetailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(AlbumDetailsViewModel::class.java)

        val args = arguments
        if (args != null && args.containsKey(ALBUM_ID_KEY)) {
            albumDetailsViewModel!!.loadAlbumById(args.getLong(ALBUM_ID_KEY)).observe(viewLifecycleOwner, Observer {
                album ->  updateUi(album)
            })
        }


    }

    fun updateUi(album: Album?) {

       album_detail.text = album?.name
       artist_name.text = album?.artist
       track_detail.text = album?.url
        //load image
       Glide.with(this).load(album?.albumImage).centerCrop().into(detail_image)


    }

    companion object {

        private val ALBUM_ID_KEY = "album_id"

        fun newInstance(): AlbumDetailsFragment {
            return AlbumDetailsFragment()
        }


        fun create(albumId: Long?): AlbumDetailsFragment {
            val albumDetailsFragment = AlbumDetailsFragment()
            val args = Bundle()
            args.putLong(ALBUM_ID_KEY, albumId!!)
            albumDetailsFragment.arguments = args
            return albumDetailsFragment
        }
    }


}

