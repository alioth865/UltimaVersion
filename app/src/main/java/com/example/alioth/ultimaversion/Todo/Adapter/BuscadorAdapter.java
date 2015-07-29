package com.example.alioth.ultimaversion.Todo.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Modelo.Categoria;
import com.example.alioth.ultimaversion.Todo.Modelo.Producto;
import com.example.alioth.ultimaversion.Todo.Util.FragmentIterationListener;
import com.example.alioth.ultimaversion.Todo.Util.MySingletonClass;
import com.example.alioth.ultimaversion.Todo.Util.Utilidades;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alioth on 01/07/2015.
 */
public class BuscadorAdapter extends BaseAdapter implements Filterable{
    private Map<Integer, Categoria> todosProductos;
    private List<Producto> productos;
    Filter filter;
    private Activity actividad;
    private int layoutResourceId;
    private String usuario;
    private String contraseña;
    private String urlBase;

    public BuscadorAdapter(Map<Integer, Categoria> todosProductos, Activity actividad, int layoutResourceId, Map<String,String> configuracion) {
        this.todosProductos = todosProductos;
        this.productos=new LinkedList<>();
        for(Categoria c:todosProductos.values()) {
            for (Producto p : c.getProductos()) {
                if (!productos.contains(p))
                    productos.add(p);
            }
        }
        this.actividad=actividad;
        this.layoutResourceId=layoutResourceId;
        this.usuario=configuracion.get("key");
        this.contraseña="";
        this.urlBase="http://"+configuracion.get("shop_url")+"/"+configuracion.get("name_shop")+"/api/images/products/";
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int position) {
        return productos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productos.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView;
        if(convertView==null){
            LayoutInflater inflater = actividad.getLayoutInflater();
            listItemView = inflater.inflate(layoutResourceId,null);
        }else{
            listItemView=convertView;
        }

        //Inicializando las vistas
        NetworkImageView imagenPost =(NetworkImageView)listItemView.findViewById(R.id.iv_imagen_producto);
        TextView id=(TextView)listItemView.findViewById(R.id.tv_id_producto);
        TextView nombre=(TextView)listItemView.findViewById(R.id.tv_nombre_producto);
        TextView referencia=(TextView)listItemView.findViewById(R.id.tv_referencia);
        Button verCombinacion=(Button)listItemView.findViewById(R.id.btn_elegir);


        //Dandolle valor
        nombre.setText(productos.get(position).getNombre());
        id.setText(productos.get(position).getId()+"");
        referencia.setText(productos.get(position).getReferencia());
        String IMAGE_URL = urlBase+"/"+productos.get(position).getId()+"/"+productos.get(position).getIdDefaultImage();
        ImageLoader imageLoader= MySingletonClass.getInstance(actividad.getApplicationContext(), usuario, contraseña).getImageLoader();//imageLoader.get(IMAGE_URL,ImageLoader.getImageListener(imagenPost,R.drawable.ic_action_reload,R.drawable.ic_action_emo_err));
        imagenPost.setImageUrl(IMAGE_URL, imageLoader);


        verCombinacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentIterationListener)actividad).onFragmentIteration(Utilidades.idFragmentElegirCombinacion, productos.get(position));
            }
        });

        return listItemView;

    }

    @Override
    public Filter getFilter() {
        if(filter==null)
            filter=new FiltroProducto();
        return filter;
    }


    private class FiltroProducto extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults resultado=new FilterResults();
            if(constraint==null || constraint.length()==0){
                List<Producto> todosProdutosLista=new LinkedList<>();
                for(Categoria c:todosProductos.values()){
                    for(Producto p:c.getProductos()){
                        if(!todosProdutosLista.contains(p))
                            todosProdutosLista.add(p);
                    }

                }
                resultado.values=todosProdutosLista;
                resultado.count=todosProdutosLista.size();
            }else{
                List<Producto> todosProdutosLista=new LinkedList<>();
                for(Categoria c:todosProductos.values()){
                    for(Producto p:c.getProductos()){
                        if(!todosProdutosLista.contains(p)){
                            if(Utilidades.isNumeric(constraint.toString())){
                                if(p.getId()==Integer.parseInt(constraint.toString())){
                                    todosProdutosLista.add(p);
                                }
                            }else{
                                if( p.getNombre().toUpperCase().startsWith(constraint.toString().toUpperCase())||
                                    p.getReferencia().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                                    todosProdutosLista.add(p);
                            }

                        }

                    }

                }
                resultado.values=todosProdutosLista;
                resultado.count=todosProdutosLista.size();
            }
            return resultado;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productos = (List<Producto>) results.values;
            notifyDataSetChanged();
        }
    }


}
