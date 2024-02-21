package com.solution_challenge.keinearmut

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)


        val cardView: CardView = view.findViewById(R.id.img)
        val cardView2: CardView = view.findViewById(R.id.img4)
        val cardView3: CardView = view.findViewById(R.id.healthcare)
        val cardView4: CardView = view.findViewById(R.id.employment)

        cardView.setOnClickListener {
            startActivity(Intent(activity, foodCard::class.java))
        }

        cardView2.setOnClickListener {
            startActivity(Intent(activity, educationCard::class.java))
        }
        cardView3.setOnClickListener {
            startActivity(Intent(activity, healthCard::class.java))
        }
        cardView4.setOnClickListener {
            startActivity(Intent(activity, employmentCard::class.java))
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}