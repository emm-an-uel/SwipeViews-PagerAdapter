package com.example.swipeviews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter

class Adapter(
    private val context: Context,
    private val modelArray: ArrayList<Model>
) : PagerAdapter() {

    override fun getCount(): Int {
        return modelArray.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // inflate layout
        val view = LayoutInflater.from(context).inflate(R.layout.card_item, container, false)
        view.tag = position

        // get data
        val model = modelArray[position]
        val tv1: TextView = view.findViewById(R.id.textView)
        val tv2: TextView = view.findViewById(R.id.textView2)
        tv1.text = model.t1
        tv2.text = model.t2

        // add view to container
        container.addView(view, position)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}