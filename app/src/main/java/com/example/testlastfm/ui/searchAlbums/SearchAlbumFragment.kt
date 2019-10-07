package com.example.testlastfm.ui.searchAlbums

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.example.testlastfm.AppExecutors
import com.example.testlastfm.R
import com.example.testlastfm.dependencyinjection.Injectable
import com.example.testlastfm.ui.common.NavigationController
import kotlinx.android.synthetic.main.fragment_search_album.*
import kotlinx.android.synthetic.main.fragment_search_album.view.*
import javax.inject.Inject

class SearchAlbumFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var navigationController: NavigationController

    private lateinit var viewModel: SearchAlbumViewModel

    private var searchAlbumAdapter: SearchAlbumAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_album, container, false)

        val recyclerView = view.album_recycler_view

        if (recyclerView is RecyclerView) {
            with(recyclerView) {
                layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                searchAlbumAdapter = SearchAlbumAdapter(appExecutors) { album -> navigationController.navigateToAlbumDetails(album.albumId) }
                adapter = searchAlbumAdapter

            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchAlbumViewModel::class.java)

        initList()

        initSearchInputListener()
    }

    private fun initSearchInputListener() {
        input?.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(view)
                true
            } else {
                false
            }
        }

        input?.setOnKeyListener { view: View, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearch(view)
                true
            } else {
                false
            }
        }
    }

    private fun doSearch(view: View) {
        val query = input?.text.toString()

        dismissKeyboard(view.windowToken)
        viewModel.setQuery(query)
    }

    private fun initList() {

        album_recycler_view?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                //TODO load next page if last position
            }
        })

        viewModel.results.observe(this, Observer { result -> searchAlbumAdapter?.submitList(result?.data) })
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}
