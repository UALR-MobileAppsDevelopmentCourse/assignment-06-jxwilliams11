package com.ualr.recyclerviewassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ualr.recyclerviewassignment.Utils.Adapter;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

// TODO 05. Create a new Adapter class and the corresponding ViewHolder class in a different file. The adapter will be used to populate
//  the recyclerView and manage the interaction with the items in the list
// TODO 06. Detect click events on the list items. Implement a new method to toggle items' selection in response to click events
// TODO 07. Detect click events on the thumbnail located on the left of every list row when the corresponding item is selected.
//  Implement a new method to delete the corresponding item in the list
// TODO 08. Create a new method to add a new item on the top of the list. Use the DataGenerator class to create the new item to be added.

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFAB;
    private RecyclerView recyclerView;
    private Adapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_multi_selection);
        initComponent();
    }

    private void initComponent() {
        // TODO 01. Generate the item list to be displayed using the DataGenerator class

        List <Inbox> itemList = DataGenerator.getInboxData(this);
        itemList.addAll(DataGenerator.getInboxData(this));
        // TODO 03. Do the setup of a new RecyclerView instance to display the item list properly

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // TODO 04. Define the layout of each item in the list

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // TODO 09. Create a new instance of the created Adapter class and bind it to the RecyclerView instance created in step 03

        mAdapter = new Adapter(this,itemList);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, Inbox obj, int position) {
                mAdapter.clear();
                obj.toggleSelection();
                mAdapter.notifyItemChanged(position);
            }

            @Override
            public void OnThumbnailClick(View view, Inbox obj, int position) {
                if(obj.isSelected())
                    mAdapter.deleteItem(position);
            }
        });

        mFAB = findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 10. Invoke the method created to a new item to the top of the list so it's
                //  triggered when the user taps the Floating Action Button

                mAdapter.addItem(0, DataGenerator.getRandomInboxItem(getApplicationContext()));
                mAdapter.notifyItemChanged(0);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

}