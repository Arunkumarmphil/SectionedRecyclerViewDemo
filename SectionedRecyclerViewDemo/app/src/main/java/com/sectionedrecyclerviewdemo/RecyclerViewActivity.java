package com.sectionedrecyclerviewdemo;

import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by sonu on 24/07/17.
 */

public class RecyclerViewActivity extends AppCompatActivity {

    protected static final String RECYCLER_VIEW_TYPE = "recycler_view_type";
    private RecyclerViewType recyclerViewType;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_activity);

        //get enum type passed from MainActivity
        recyclerViewType = (RecyclerViewType) getIntent().getSerializableExtra(RECYCLER_VIEW_TYPE);
      //  setUpToolbarTitle();
        createFile();
        readTextFile();
        //setUpRecyclerView();
        //populateRecyclerView();
        
    }

    private void createFile() {
        try {
            // Creates a file in the primary external storage space of the
            // current application.
            // If the file does not exists, it is created.
            File testFile = new File(this.getExternalFilesDir(null), "TestFile.txt");
            if (!testFile.exists())
                testFile.createNewFile();

            // Adds a line to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(testFile, true /*append*/));
            writer.write("This is a test file.");
            writer.close();
            // Refresh the data so it can seen when the device is plugged in a
            // computer. You may have to unplug and replug the device to see the
            // latest changes. This is not necessary if the user should not modify
            // the files.
            MediaScannerConnection.scanFile(this,
                    new String[]{testFile.toString()},
                    null,
                    null);




            InputStream inputStream = getResources().openRawResource(R.raw.arun);
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            String eachline = null;
            try {
                eachline = bufferedReader.readLine();
                System.out.println("File Content is:: "+eachline);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            Log.e("ReadWriteFile", "Unable to write to the TestFile.txt file.");
        }
    }

    private void readTextFile()
    {

        InputStream inputStream = getResources().openRawResource(R.raw.arun);
        BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
        String eachline = null;
        try {
            eachline = bufferedReader.readLine();
            System.out.println("File Content is:: "+eachline);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //set toolbar title and set back button
    private void setUpToolbarTitle() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        switch (recyclerViewType) {
            case LINEAR_HORIZONTAL:
                getSupportActionBar().setTitle(getResources().getString(R.string.linear_sectioned_recyclerview_horizontal));
                break;
            case LINEAR_VERTICAL:
                getSupportActionBar().setTitle(getResources().getString(R.string.linear_sectioned_recyclerview_vertical));
                break;
            case GRID:
                getSupportActionBar().setTitle(getResources().getString(R.string.grid_sectioned_recyclerview));
                break;

        }
    }

    //setup recycler view
    private void setUpRecyclerView()
    {
        recyclerView = (RecyclerView) findViewById(R.id.sectioned_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    //populate recycler view
    private void populateRecyclerView()
    {
        ArrayList<SectionModel> sectionModelArrayList = new ArrayList<>();
        //for loop for sections
        for (int i = 1; i <= 5; i++) {
            ArrayList<String> itemArrayList = new ArrayList<>();
            //for loop for items
            for (int j = 1; j <= 10; j++) {
                itemArrayList.add("Item " + j);
            }

            //add the section and items to array list
            sectionModelArrayList.add(new SectionModel("Section " + i, itemArrayList));
        }

        SectionRecyclerViewAdapter adapter = new SectionRecyclerViewAdapter(this, recyclerViewType, sectionModelArrayList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
