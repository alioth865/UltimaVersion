package com.example.alioth.ultimaversion.Todo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Modelo.Combinacion;
import com.example.alioth.ultimaversion.Todo.Modelo.OpcionesProductos;
import com.example.alioth.ultimaversion.Todo.Modelo.Talla;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by AliothAntonio on 29/07/2015.
 */
public class TallaSpinnerAdapter extends ArrayAdapter<Combinacion> {
    private Activity context;
    private int resource;
    List<Combinacion> datos=null;

    public TallaSpinnerAdapter(Activity context, int resource, Set<Combinacion> datos) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
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
        View mySpinner= inflater.inflate(resource, null);
        TextView tallaTV=(TextView)mySpinner.findViewById(R.id.talla_tv);
        List<OpcionesProductos> opcionesProductoses=datos.get(position).getOpcionesProductosList();
        for(OpcionesProductos op:opcionesProductoses){
            if(op instanceof Talla)
                tallaTV.setText(((Talla)op).getTalla());
        }
        return mySpinner;
    }

    @Override
    public int getCount() {
        return datos.size();
    }


    @Override
    public Combinacion getItem(int position) {
        return datos.get(position);
    }
}
