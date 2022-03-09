package com.example.senku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    TextView move_counter;

    int[][] grid1 = {
            {0, 0, 2, 2, 2, 0, 0},
            {0, 0, 2, 1, 2, 0, 0},
            {2, 2, 2, 1, 2, 2, 2},
            {2, 1, 1, 1, 1, 1, 2},
            {2, 2, 2, 1, 2, 2, 2},
            {0, 0, 2, 1, 2, 0, 0},
            {0, 0, 2, 2, 2, 0, 0}
    };

    int[][] grid2 = {
            {0, 0, 2, 1, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 0},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {0, 1, 1, 1, 1, 1, 0},
            {0, 0, 1, 1, 1, 0, 0}
    };

    int[][] grid3 = {
            {0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 1, 1, 1, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 2, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 1, 1, 1, 0, 0, 0}
    };

    int[][] grid4 = {
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0}
    };


    int[][] grid5 = {

            {0, 0, 0, 2, 2, 2, 0, 0, 0},
            {0, 0, 0, 2, 2, 2, 0, 0, 0},
            {0, 0, 0, 2, 2, 2, 0, 0, 0},
            {2, 2, 2, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 2},
            {2, 1, 1, 2, 2, 2, 2, 2, 2},
            {0, 0, 0, 2, 2, 2, 0, 0, 0},
            {0, 0, 0, 2, 2, 2, 0, 0, 0},
            {0, 0, 0, 2, 2, 2, 0, 0, 0}
    };

    int[][] selectedGrid = grid1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        move_counter = findViewById(R.id.move_counter);

        ArrayList<CellModel> cellModelArrayList = new ArrayList<CellModel>();

        Random rand = new Random();
        int n = rand.nextInt(3);

        switch (n) {
            case 0:
                selectedGrid = grid1;
                break;
            case 1:
                selectedGrid = grid2;
                break;
            case 2:
                selectedGrid = grid3;
                break;
            case 3:
                selectedGrid = grid4;
                break;
            default:
                break;
        }

        selectedGrid = grid1;
        gridView.setNumColumns(selectedGrid.length);

        for (int i = 0; i < selectedGrid.length; i++) {
            for (int j = 0; j < selectedGrid.length; j++) {

                switch (selectedGrid[i][j]) {
                    case 0:
                        cellModelArrayList.add(new CellModel(0, 0,
                                selectedGrid.length, i, j));
                        break;
                    case 1:
                        cellModelArrayList.add(new CellModel(1, 1,
                                selectedGrid.length, i, j));
                        break;
                    case 2:
                        cellModelArrayList.add(new CellModel(1, 0,
                                selectedGrid.length, i, j));
                        break;
                    default:
                        break;
                }
            }
        }


        Chronometer timer = (Chronometer) findViewById(R.id.timer);

        CellAdapter cellAdapter = new CellAdapter(this, cellModelArrayList,
                selectedGrid, move_counter, timer);
        gridView.setAdapter(cellAdapter);

    }
}