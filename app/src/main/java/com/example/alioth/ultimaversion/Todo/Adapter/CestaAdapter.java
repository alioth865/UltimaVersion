package com.example.alioth.ultimaversion.Todo.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Modelo.Cesta;
import com.example.alioth.ultimaversion.Todo.Modelo.Combinacion;
import com.example.alioth.ultimaversion.Todo.Modelo.Producto;

import java.util.List;
import java.util.Map;

/**
 * Created by AliothAntonio on 29/07/2015.
 */
public class CestaAdapter extends BaseAdapter {
    private Cesta cesta;
    private Activity activity;
    private int resource;
    Map<Integer,Producto> todosProductos;

    public CestaAdapter(Cesta cesta, Activity activity, int resource, Map<Integer,Producto> todosProductos) {
        this.cesta = cesta;
        this.activity = activity;
        this.resource = resource;
        this.todosProductos=todosProductos;
    }

    @Override
    public int getCount() {
        return cesta.getCesta().size();
    }

    @Override
    public Object getItem(int position) {
        return cesta.getCesta().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(resource,null);
        }

        TextView tvProducto =(TextView)convertView.findViewById(R.id.textView26);
        TextView tvCantidad =(TextView)convertView.findViewById(R.id.textView27);
        ImageButton cancelar=(ImageButton)convertView.findViewById(R.id.button6);

        tvCantidad.setText(cesta.getCesta().get(position).getCantidad()+"");

        int idProducto=cesta.getCesta().get(position).getCombinacion().getIdProducto();
        String nombreProducto=todosProductos.get(idProducto).getNombre();

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //COMMIT
                cesta.eliminar(position);
                notifyDataSetChanged();
            }
        });


        tvProducto.setText(nombreProducto + " "+ cesta.getCesta().get(position).getCombinacion().toString());

        return convertView;
    }
}
