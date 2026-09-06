package com.android.mykoodugalapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.android.mykoodugalapplication.R
import com.android.mykoodugalapplication.activity.SparrowDetailsActivity
import com.android.mykoodugalapplication.activity.SparrowSpottedActivity

class NestInstalledFragment :Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.activity_spotted_sparrow_anywhere, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardNestBox = view.findViewById<CardView>(R.id.cardNestBox)
        val cardSparrow = view.findViewById<CardView>(R.id.cardSparrow)

        cardNestBox.setOnClickListener {
            val intent= Intent(context,SparrowDetailsActivity::class.java)
            startActivity(intent)
        }


        cardSparrow.setOnClickListener {
            val intent= Intent(context, SparrowSpottedActivity::class.java)
            startActivity(intent)
        }


    }
}