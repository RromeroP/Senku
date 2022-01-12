package com.example.senku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class CellAdapter extends ArrayAdapter<CellModel> {

    public CellAdapter(@NonNull Context context, ArrayList<CellModel> cellModelArrayList) {
        super(context, 0, cellModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.cells, parent, false);
        }

        CellModel cellModel = getItem(position);

        ImageView cellBg = listItemView.findViewById(R.id.cellBg);
        ImageView cellCircle = listItemView.findViewById(R.id.cellCircle);

        cellBg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));
        cellCircle.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.empty_cell));

        return listItemView;
    }
}

