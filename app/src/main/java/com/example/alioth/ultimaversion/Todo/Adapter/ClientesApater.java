package com.example.alioth.ultimaversion.Todo.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Modelo.Categoria;
import com.example.alioth.ultimaversion.Todo.Modelo.Cliente;
import com.example.alioth.ultimaversion.Todo.Util.FragmentIterationListener;
import com.example.alioth.ultimaversion.Todo.Util.Utilidades;
import com.example.alioth.ultimaversion.Todo.Vistas.Principal;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alioth on 26/06/2015.
 */
public class ClientesApater extends BaseAdapter{
    private List<Cliente> todosClientes;
    private Map<Integer,Categoria> todasCategoriasMap;
    private Activity actividad;

    public ClientesApater(Collection<Cliente> todosClientes, Map<Integer,Categoria> todasCategoriasMap, Activity actividad) {
        this.todosClientes = new LinkedList<>(todosClientes);
        this.todasCategoriasMap=todasCategoriasMap;
        this.actividad=actividad;
    }

    @Override
    public int getCount() {
        return todosClientes.size();
    }

    @Override
    public Object getItem(int position) {
        return todosClientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return todosClientes.get(position).getIdCliente();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = actividad.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_cliente,null);
        }
        TextView nombreApellido=(TextView)convertView.findViewById(R.id.textView8);
        TextView email=(TextView)convertView.findViewById(R.id.textView9);
        TextView totalVentas=(TextView)convertView.findViewById(R.id.textView10);
        ImageButton crearPedido=(ImageButton)convertView.findViewById(R.id.imageButton);
        nombreApellido.setText(todosClientes.get(position).getFirstname()+" "+todosClientes.get(position).getLastname());
        email.setText(todosClientes.get(position).getEmail());
        totalVentas.setText("Ventas "+String.format("%.2f",todosClientes.get(position).getVentas().totalVentas())+" €");
        crearPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Principal)actividad).onFragmentIteration(Utilidades.idFragmentCrearPedido, todosClientes.get(position), todasCategoriasMap);

            }
        });
        return convertView;
    }


}
