package com.example.swipeviews

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager

class MainActivity : AppCompatActivity() {

    private val MIN_OFFSET = 0f
    private val MAX_OFFSET = 0.5f
    private val MIN_ALPHA = 0.5f
    private val MIN_SCALE = 0.8f

    private lateinit var modelList: ArrayList<Model>
    private lateinit var adapter: Adapter
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadCards()
        viewPager.offscreenPageLimit = 3 // loads (position + 2) in time, prevents crash
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // update view scale and alpha
                updatePager(viewPager.findViewWithTag(position), 1f - positionOffset) // current page
                if ((position + 1) < modelList.size) { // next page
                    updatePager(viewPager.findViewWithTag(position + 1), positionOffset)
                }
                if ((position + 2) < modelList.size) { // two pages in advance
                    // (so it's already made smaller before user can see it - smoother look)
                    updatePager(viewPager.findViewWithTag(position + 2), 0f)
                }
                if ((position - 1) >= 0) { // previous page
                    updatePager(viewPager.findViewWithTag(position - 1), 0f)
                }
            }

            override fun onPageSelected(position: Int) {
                viewPager.findViewWithTag<View>(position).setOnClickListener { // selected page click listener
                    val actualPos = position + 1
                    Toast.makeText(this@MainActivity, "You clicked on $actualPos", Toast.LENGTH_SHORT).show()
                }

                // remove click listeners for previous and next pages
                viewPager.findViewWithTag<View>(position - 1).setOnClickListener(null)
                viewPager.findViewWithTag<View>(position + 1).setOnClickListener(null)
            }

            override fun onPageScrollStateChanged(state: Int) {
                // do nothing
            }
        })
    }

    private fun updatePager(view: View, offset: Float) {
        var adjustedOffset: Float =
            (1.0f - 0.0f) * (offset - MIN_OFFSET) / (MAX_OFFSET - MIN_OFFSET) + 0.0f
        adjustedOffset = if (adjustedOffset > 1f) 1f else adjustedOffset
        adjustedOffset = if (adjustedOffset < 0f) 0f else adjustedOffset

        val alpha: Float =
            adjustedOffset * (1f - MIN_ALPHA) + MIN_ALPHA
        val scale: Float =
            adjustedOffset * (1f - MIN_SCALE) + MIN_SCALE

        view.alpha = alpha
        view.scaleY = scale
    }

    private fun loadCards() {
        modelList = arrayListOf(
            Model("String 1a", "String 1b"),
            Model("String 2a", "String 2b"),
            Model("String 3a", "String 3b"),
            Model("String 4a", "String 4b"),
            Model("String 5a", "String 5b"),
        )

        adapter = Adapter(this, modelList)
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = adapter
        viewPager.setPadding(100, 0, 100, 0)
    }
}