package com.example.alioth.ultimaversion.Todo.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Modelo.Categoria;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Alioth on 19/06/2015.
 */
public class CategoriasAdapter extends BaseAdapter {
    private List<Categoria> categorias;
    private Activity actividad;
    private int layoutResourceId;

    public CategoriasAdapter(Collection<Categoria> categorias,Activity actividad,  int layoutResourceId) {
        this.categorias = new LinkedList<>(categorias);
        this.actividad=actividad;
        this.layoutResourceId=layoutResourceId;
    }

    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int position) {
        return categorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categorias.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater = actividad.getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId,null);
        }
        TextView tvNombre =(TextView)convertView.findViewById(R.id.tv_nombre_categoria);
        TextView tvID =(TextView)convertView.findViewById(R.id.tv_id_categoria);
        tvNombre.setText(categorias.get(position).getName());
        tvID.setText(categorias.get(position).getId()+"");
        return convertView;
    }
}
