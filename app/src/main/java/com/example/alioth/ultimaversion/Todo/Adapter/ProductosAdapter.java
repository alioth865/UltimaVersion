package com.example.alioth.ultimaversion.Todo.Adapter;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Modelo.Producto;
import com.example.alioth.ultimaversion.Todo.Util.MySingletonClass;
/*import com.example.alioth.ultimaversion.Todo.Util.MyInterceptor;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;*/

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alioth on 19/06/2015.
 */
public class ProductosAdapter extends BaseAdapter {
    private List<Producto> productos;
    private Activity actividad;
    private int layoutResourceId;
    private String usuario;
    private String contraseña;
    private String urlBase;

    public ProductosAdapter(List<Producto> productos, Activity actividad, int layoutResourceId, Map<String,String> configuracion) {
        this.productos = productos;
        this.actividad = actividad;
        this.layoutResourceId = layoutResourceId;
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
    public View getView(int position, View convertView, ViewGroup parent) {
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


        //Dandolle valor
        nombre.setText(productos.get(position).getNombre());
        id.setText(productos.get(position).getId()+"");
        referencia.setText(productos.get(position).getReferencia());
        String IMAGE_URL = urlBase+"/"+productos.get(position).getId()+"/"+productos.get(position).getIdDefaultImage();
        ImageLoader imageLoader= MySingletonClass.getInstance(actividad.getApplicationContext(),usuario,contraseña).getImageLoader();//imageLoader.get(IMAGE_URL,ImageLoader.getImageListener(imagenPost,R.drawable.ic_action_reload,R.drawable.ic_action_emo_err));
        imagenPost.setImageUrl(IMAGE_URL, imageLoader);
        return listItemView;
    }
}
