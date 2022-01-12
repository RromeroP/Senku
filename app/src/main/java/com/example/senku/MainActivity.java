package com.example.senku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);

        ArrayList<CellModel> cellModelArrayList = new ArrayList<CellModel>();
        cellModelArrayList.add(new CellModel("90",0,0));
        cellModelArrayList.add(new CellModel("90",1,1));

        CellAdapter cellAdapter = new CellAdapter(this, cellModelArrayList);
        gridView.setAdapter(cellAdapter);
    }

}