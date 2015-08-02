package com.example.alioth.ultimaversion.Todo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Modelo.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Alioth on 28/07/2015.
 */
public class ColorSpinnerAdapter extends ArrayAdapter<Color>{
    private Activity context;
    private int viewResourceId;
    List<Color> datos=null;

    public ColorSpinnerAdapter(Activity context, int viewResourceId, Set<Color> datos) {
        super(context, viewResourceId);
        this.context = context;
        this.viewResourceId = viewResourceId;
        this.datos = new LinkedList<>(datos);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View mySpinner= inflater.inflate(viewResourceId, null);
        TextView nombreColor=(TextView)mySpinner.findViewById(R.id.nombreColorTV);
        ImageView imageViewColor=(ImageView)mySpinner.findViewById(R.id.colorIV);

        nombreColor.setText(datos.get(position).getNombreColor());
        imageViewColor.setBackgroundColor(datos.get(position).getColor());

        return mySpinner;
    }



    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Color getItem(int position) {
        return datos.get(position);
    }
}
