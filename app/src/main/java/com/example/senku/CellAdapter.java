package com.example.senku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class CellAdapter extends ArrayAdapter<CellModel> {
    int[][] grid;
    boolean selected = false;
    GridView gridView;
    ViewGroup viewGroup;

    public CellAdapter(@NonNull Context context, ArrayList<CellModel> cellModelArrayList,
                       int[][] selectedGrid, GridView gV) {
        super(context, 0, cellModelArrayList);
        grid = selectedGrid;
        gridView = gV;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        viewGroup = parent;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.cells, parent, false);
        }

        CellModel cellModel = getItem(position);

        CardView cellCard = listItemView.findViewById(R.id.cellCard);
        ImageView cellBg = listItemView.findViewById(R.id.cellBg);
        ImageView cellCircle = listItemView.findViewById(R.id.cellCircle);

        //We check if the grid is 8x8 and adjust the size
        if (cellModel.getGridSize() == 8) {
            int cellSize = (int) getContext().getResources().getDimension(R.dimen.cell_size_sm);
            cellCard.setLayoutParams(new ViewGroup.LayoutParams(cellSize, cellSize));
        }

        //We check whether it should be and empty cell or not
        if (cellModel.getCircleId() == 1) {
            cellCircle.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_token));
        } else {
            cellCircle.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_transparent));
        }

        //We check whether it should be a cell (we add a listener if it is) or not
        if (cellModel.getBgId() == 1) {
            cellBg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_bg));

            listItemView.setOnClickListener(view -> checkClick(cellModel, cellCircle));

        } else {
            cellBg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_transparent));
        }

        return listItemView;
    }

    private void checkClick(CellModel cellModel, ImageView cellCircle) {
        if (!selected && grid[cellModel.getRow()][cellModel.getCol()] == 1) {
            //Select valid cell
            cellCircle.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_selected));

            checkDirection(cellModel);

            cellModel.setSelected(true);
            selected = true;

        } else if (selected && cellModel.isSelected()) {
            //Deselect selected cell
            cellCircle.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_token));

            checkDirection(cellModel);

            cellModel.setSelected(false);
            selected = false;

        } else if (cellModel.isPossible()) {
            checkDirection(cellModel);
        }
    }

    private void checkDirection(CellModel cellModel) {
        //Check if the 2 top cells are not out of bounds
        boolean topBounds = cellModel.getRow() - 1 >= 0 && cellModel.getRow() - 2 >= 0;

        //Check if the 2 bottom cells are not out of bounds
        boolean botBounds = cellModel.getRow() + 1 < grid.length && cellModel.getRow() + 2 < grid.length;

        //Check if the 2 left cells are not out of bounds
        boolean leftBounds = cellModel.getCol() - 1 >= 0 && cellModel.getCol() - 2 >= 0;

        //Check if the 2 right cells are not out of bounds
        boolean rightBounds = cellModel.getCol() + 1 < grid.length && cellModel.getCol() + 2 < grid.length;

        if (topBounds) {
            checkPossibles(cellModel, "UP");
        }

        if (botBounds) {
            checkPossibles(cellModel, "DOWN");
        }

        if (leftBounds) {
            checkPossibles(cellModel, "LEFT");
        }

        if (rightBounds) {
            checkPossibles(cellModel, "RIGHT");
        }
    }

    private void checkPossibles(CellModel cellModel, String direction) {
        int y = 0;
        int x = 0;

        switch (direction) {
            case "UP":
                y = -1;
                break;
            case "DOWN":
                y = 1;
                break;
            case "LEFT":
                x = -1;
                break;
            case "RIGHT":
                x = 1;
                break;
            default:
                break;
        }

        //We get the value on the grid
        int pos1 = grid[cellModel.getRow() + y][cellModel.getCol() + x];
        int pos2 = grid[cellModel.getRow() + (y * 2)][cellModel.getCol() + (x * 2)];

        //We get the index and using it we get the cell
        int index = grid.length * (cellModel.getRow()) + (cellModel.getCol());
        int index1 = grid.length * (cellModel.getRow() + y) + (cellModel.getCol() + x);
        int index2 = grid.length * (cellModel.getRow() + (y * 2)) + (cellModel.getCol() + (x * 2));

        ImageView imageView = viewGroup.getChildAt(index).findViewById(R.id.cellCircle);
        ImageView imageView1 = viewGroup.getChildAt(index1).findViewById(R.id.cellCircle);
        ImageView imageView2 = viewGroup.getChildAt(index2).findViewById(R.id.cellCircle);

        CellModel cell = getItem(index2);

        if (pos1 == 1 && pos2 == 2) {
            //We mark all possible moves
            if (!selected) {
                imageView2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_possible));
                cell.setPossible(true);
            } else {
                imageView2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_transparent));
                cell.setPossible(false);
            }
        }

        //We move the token 2 positions a remove de middle one
        if (cell.isSelected() && getItem(index).isPossible()) {

            imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_token));
            imageView1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_transparent));
            imageView2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_transparent));

            grid[cellModel.getRow()][cellModel.getCol()] = 1;
            grid[cellModel.getRow() + y][cellModel.getCol() + x] = 2;
            grid[cellModel.getRow() + (y * 2)][cellModel.getCol() + (x * 2)] = 2;

            selected = false;
            getItem(index).setSelected(false);
            getItem(index).setPossible(false);

            getItem(index1).setPossible(false);

            getItem(index2).setPossible(false);

            removePossible();
        }

    }

    private void removePossible() {
        CellModel cell;
        for (int i = 0; i < grid.length * grid.length; i++) {
            cell = getItem(i);
            if (cell.isPossible()) {
                cell.setPossible(false);
                viewGroup.getChildAt(i).findViewById(R.id.cellCircle).setBackground(
                        ContextCompat.getDrawable(getContext(), R.drawable.cell_transparent));
            }
        }
    }

}

