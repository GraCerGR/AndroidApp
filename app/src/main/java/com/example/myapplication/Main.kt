package com.example.myapplication

import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog

class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       /* val bottomSheetFragment = BottomSheetFragment()
        bottomSheetFragment.*/

        val bottomSheetFragment = BottomSheetFragment()
/*        var button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener{
            bottomSheetFragment.show(supportFragmentManager,"Bottom")
        }*/

       /* BottomSheetFragment.setOnClickListener {

            bottomSheetFragment.show(supportFragmentManager,"Bottom")
            *//*val setIntent = Intent(this, BottomSheetFragment::class.java)
            startActivity(setIntent)*//*
        }*/



        var assignment = findViewById<TextView>(R.id.Assignment)
        assignment.setOnTouchListener { v, _ ->
            val item = ClipData.Item(assignment.text)

            // Create a new ClipData using the tag as a label, the plain text
            // MIME type, and the already-created item. This creates a new
            // ClipDescription object within the ClipData and sets its MIME type
            // to "text/plain".
            val dragData = ClipData(
                assignment.text,
                arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                item)

            // Instantiate the drag shadow builder.
            val myShadow = View.DragShadowBuilder(v)

            // Start the drag.
            v.startDragAndDrop(dragData,  // The data to be dragged.
                myShadow,  // The drag shadow builder.
                null,      // No need to use local data.
                0          // Flags. Not currently used, set to 0.
            )

            // Indicate that the long-click is handled.
            true

        }

        }

    }
