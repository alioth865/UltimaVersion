package com.example.alioth.ultimaversion.Todo.Vistas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Util.MySingletonClass;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Alioth on 25/06/2015.
 */
public class VistaImagenFragment extends Fragment {
    private static final String ID_IMAGEN="idImagen";
    private static final String ID_PRODUCTO="idProducto";
    private static final String URL_BASE="URL_BASE";
    private static final String USSER="USSER";
    private static final String PASSWD="USSER";
    private int idImagen;
    private int idProducto;
    private String urlBase;
    private String usuario;
    private String contraseña;

    public static VistaImagenFragment newInstance(int idImagen, int idProducto, Map<String,String> configuracion){
        VistaImagenFragment fragment=new VistaImagenFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(ID_IMAGEN,idImagen);
        bundle.putInt(ID_PRODUCTO,idProducto);
        bundle.putString(URL_BASE, "http://" + configuracion.get("shop_url") + "/" + configuracion.get("name_shop") + "/api/images/products/");
        bundle.putString(USSER,configuracion.get("key"));
        bundle.putString(PASSWD,"");
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            this.idImagen=getArguments().getInt(ID_IMAGEN);
            this.idProducto=getArguments().getInt(ID_PRODUCTO);
            this.urlBase=getArguments().getString(URL_BASE);
            this.usuario=getArguments().getString(USSER);
            this.contraseña=getArguments().getString(PASSWD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootview=(ViewGroup) inflater.inflate(R.layout.imagen_vista, container, false);
        NetworkImageView imagenPost =(NetworkImageView)rootview.findViewById(R.id.niv_imagen_vista);
        imagenPost.setDefaultImageResId(R.drawable.ic_action_reload);
        imagenPost.setErrorImageResId(R.drawable.ic_action_emo_err);
        String IMAGE_URL = urlBase+"/"+idProducto+"/"+idImagen;
        ImageLoader imageLoader= MySingletonClass.getInstance(getActivity().getApplicationContext(), usuario, contraseña).getImageLoader();
        imagenPost.setImageUrl(IMAGE_URL, imageLoader);
        return rootview;
    }
}
