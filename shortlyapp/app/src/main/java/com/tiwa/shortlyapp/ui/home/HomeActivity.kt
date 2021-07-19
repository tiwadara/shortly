package com.tiwa.shortlyapp.ui.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.RESULT_UNCHANGED_SHOWN
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiwa.common.data.model.ShortLinkData
import com.tiwa.common.data.state.ShortLinkState
import com.tiwa.shortlyapp.R
import com.tiwa.shortlyapp.ui.history.HistoryAdapter
import com.tiwa.shortlyapp.ui.history.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_history.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var adapter: HistoryAdapter
    private var shortLinks = mutableListOf<ShortLinkData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initUI()
        observeState()
    }

    private fun initUI() {
        initializeAdapter()
        edit_text_short_link.setOnFocusChangeListener { _, _ ->
            edit_text_short_link.hint = getString(R.string.msg_shorten_a_link_here)
            edit_text_short_link.setHintTextColor(ResourcesCompat.getColor(resources, R.color.text_color, null))
            edit_text_short_link.background = ContextCompat.getDrawable(this, R.drawable.bg_edit_text)
        }
        button_shorten.setOnClickListener {
            hideKeyboard()
            if (edit_text_short_link.text.isNullOrEmpty()) {
                edit_text_short_link.hint = getString(R.string.msg_please_add_a_link_here)
                edit_text_short_link.setHintTextColor(ResourcesCompat.getColor(resources, R.color.error, null))
                edit_text_short_link.background = ContextCompat.getDrawable(this, R.drawable.bg_edit_text_error)
            } else {
                viewModel.createShortLink(edit_text_short_link.text.toString())
            }
        }
    }

    private fun initializeAdapter() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = HistoryAdapter(
                shortLinks,
                { position, id ->
                    viewModel.deleteShortLink(id)
                    shortLinks.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    adapter.notifyItemRangeChanged(position, shortLinks.size)
                }, { url ->
            val clipboard = ContextCompat.getSystemService(this, ClipboardManager::class.java)
            val clip = ClipData.newPlainText(getString(R.string.title_shortened_url), url)
            clipboard?.setPrimaryClip(clip)
            toastMessage(getString(R.string.msg_url_copied))

        }, this)
        recycler_view.adapter = adapter
    }

    private fun observeState() {
        viewModel.state.asLiveData().observe(this, {
            handleState(it)
        })
        viewModel.getHistory()
    }

    private fun handleState(state: ShortLinkState<Any>?) {
        when (state) {
            ShortLinkState.Loading -> {
                toggleLoader(true)
            }
            is ShortLinkState.NewShortLinkReturned -> {
                toggleLoader(false)
            }
            is ShortLinkState.Failed<*> -> {
                toggleLoader(false)
                toastMessage(state.data)
            }
            is ShortLinkState.ShortLinkListReturned -> {
                toggleLoader(false)
                state.data.asLiveData().observe(this, {
                    shortLinks = it as MutableList<ShortLinkData>
                    updateRecyclerView()
                })

            }
        }
    }

    private fun toastMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun updateRecyclerView() {
        if (shortLinks.isNotEmpty()) {
            layout_create_short_link.visibility = View.GONE
            layout_history.visibility = View.VISIBLE
        } else {
            layout_create_short_link.visibility = View.VISIBLE
            layout_history.visibility = View.GONE
        }
        initializeAdapter()
    }

    private fun toggleLoader(show: Boolean) {
        if (show) progress.visibility = View.VISIBLE else progress.visibility = View.GONE
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(layout_bottom_view.applicationWindowToken, RESULT_UNCHANGED_SHOWN)
    }
}
