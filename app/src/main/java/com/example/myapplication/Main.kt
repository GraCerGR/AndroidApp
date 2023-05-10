package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout

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


        /*        var assignment = findViewById<TextView>(R.id.Assignment)
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

        }*/
        /*var varBlock = findViewById<>(R.id.VarBlock)*/

        /*val rootLayout: RelativeLayout = findViewById(R.id.drawerLayout)*/
        val secondActivityContainer: FrameLayout = findViewById(R.id.secondActivityContainer)

        var assignment = findViewById<Button>(R.id.Assignment)
        val navigationView = findViewById<com.google.android.material.navigation.NavigationView>(R.id.navigationView)
        /*var layout = findViewById<LinearLayout>(R.id.container)*/
        assignment.setOnClickListener {
            /*val rootView: ViewGroup = findViewById(android.R.id.content)
            val inflater: LayoutInflater = LayoutInflater.from(this)

            val layout = inflater.inflate(R.layout.item_assignment1, rootView, false)
            rootView.addView(layout)*/

            val inflater: LayoutInflater = LayoutInflater.from(this)
            val layout = inflater.inflate(R.layout.assignment_block1, secondActivityContainer, false)
            secondActivityContainer.addView(layout)

            val drawerLayout = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawerLayout) // Получение ссылки на DrawerLayout
            drawerLayout.closeDrawer(navigationView) // Закрытие NavigationView

        }
    }
}




