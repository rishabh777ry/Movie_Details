package com.example.myapplication

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.homePageHeading)
        val slideToTop = AnimationUtils.loadAnimation(this, R.anim.slide_to_top)
        val frameName =  "movieSearch"




        slideToTop.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
                holdTextViewPosition(textView)
                supportFragmentManager.beginTransaction().add(R.id.frameLayout,MovieSearch(),frameName).commit()

            }
            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
        textView.startAnimation(slideToTop)
    }

    private fun holdTextViewPosition(textView: TextView) {
        val parent = textView.parent as ConstraintLayout
        val constraintSet = ConstraintSet()
        constraintSet.clone(parent)
        constraintSet.clear(textView.id, ConstraintSet.BOTTOM)
        constraintSet.clear(textView.id, ConstraintSet.TOP)
        constraintSet.connect(
            textView.id, ConstraintSet.TOP,
            ConstraintSet.PARENT_ID, ConstraintSet.TOP
        )
        constraintSet.applyTo(parent)
        textView.requestLayout()
    }
}
