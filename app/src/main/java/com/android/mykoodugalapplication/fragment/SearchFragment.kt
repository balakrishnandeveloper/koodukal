package com.android.mykoodugalapplication.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.mykoodugalapplication.R
import com.android.mykoodugalapplication.adapter.ImageAdapter
import com.android.mykoodugalapplication.dataClass.BirdImage

class SearchFragment : Fragment() {

    private lateinit var adapter: ImageAdapter
    private lateinit var fullList: List<BirdImage>

    private var handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val etSearch = view.findViewById<EditText>(R.id.etSearch)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        // 🔥 Dummy Data (Replace later with Firebase/API)
        fullList = listOf(
            BirdImage(R.drawable.sparrow1, "chennai"),
            BirdImage(R.drawable.sparrow2, "chennai"),
            BirdImage(R.drawable.sparrow2, "madurai"),
            BirdImage(R.drawable.sparrow3, "coimbatore"),
            BirdImage(R.drawable.sparrow1, "trichy"),
            BirdImage(R.drawable.sparrow3, "chennai")
        )

        adapter = ImageAdapter(fullList)
        recyclerView.adapter = adapter

        // 🔍 AUTO SEARCH WHILE TYPING
        etSearch.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                runnable?.let { handler.removeCallbacks(it) }

                runnable = Runnable {

                    val query = s.toString().lowercase()

                    val filteredList = if (query.isEmpty()) {
                        fullList
                    } else {
                        fullList.filter {
                            it.location.contains(query)
                        }
                    }

                    adapter.updateList(filteredList)
                }

                handler.postDelayed(runnable!!, 300) // smooth typing delay
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }
}