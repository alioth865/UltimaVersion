package com.example.alioth.ultimaversion.Todo.Vistas;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Adapter.ColorSpinnerAdapter;
import com.example.alioth.ultimaversion.Todo.Adapter.TallaSpinnerAdapter;
import com.example.alioth.ultimaversion.Todo.Modelo.Color;
import com.example.alioth.ultimaversion.Todo.Modelo.Combinacion;
import com.example.alioth.ultimaversion.Todo.Modelo.Producto;
import com.example.alioth.ultimaversion.Todo.Util.FragmentIterationListener;
import com.example.alioth.ultimaversion.Todo.Util.MySingletonClass;
import com.example.alioth.ultimaversion.Todo.Util.Utilidades;
import com.squareup.okhttp.internal.Util;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;


public class ElegirCombinationFragment extends Fragment {
    private static final String PRODUCTO = "PRODUCTO";
    private static final String URL_BASE="URL_BASE";
    private static final String USSER="USSER";
    private static final String PASSWD="USSER";
    private String usuario;
    private String contraseña;
    private String urlBase;
    private Producto producto;
    private Spinner tallaSpinner;
    private Spinner colorSpinner;
    private TextView cantDisponiblesTV;
    private EditText cantReservarET;
    private Color color;
    private NetworkImageView foto;
    private Button añadirCarro;
    private Button cancelar;


    private FragmentIterationListener comm;

    public static ElegirCombinationFragment newInstance(Producto producto, Map<String,String> configuracion) {
        ElegirCombinationFragment fragment = new ElegirCombinationFragment();
        Bundle args = new Bundle();
        args.putSerializable(PRODUCTO, producto);
        args.putString(URL_BASE, "http://" + configuracion.get("shop_url") + "/" + configuracion.get("name_shop") + "/api/images/products/");
        args.putString(USSER,configuracion.get("key"));
        args.putString(PASSWD,"");
        fragment.setArguments(args);
        return fragment;
    }

    public ElegirCombinationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.producto = (Producto)getArguments().getSerializable(PRODUCTO);
            this.urlBase=getArguments().getString(URL_BASE);
            this.usuario=getArguments().getString(USSER);
            this.contraseña=getArguments().getString(PASSWD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_elegir_combination, null);
        init(root);
        return root;
    }

    private void init(ViewGroup root) {
        this.colorSpinner=(Spinner)root.findViewById(R.id.spinner4);
        this.tallaSpinner=(Spinner)root.findViewById(R.id.spinner3);
        this.cantDisponiblesTV=(TextView)root.findViewById(R.id.textView19);
        this.cantReservarET=(EditText)root.findViewById(R.id.editText4);
        this.añadirCarro=(Button)root.findViewById(R.id.button5);
        this.cancelar=(Button)root.findViewById(R.id.button4);
        this.foto=(NetworkImageView)root.findViewById(R.id.niv_imagen_combinacion);
        foto.setDefaultImageResId(R.drawable.ic_action_reload);
        foto.setErrorImageResId(R.drawable.ic_action_emo_err);

        colorSpinner.setAdapter(new ColorSpinnerAdapter(getActivity(),R.layout.item_color,Utilidades.colores(producto.getCombinacions())));
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Color color=(Color)parent.getItemAtPosition(position);
                Set<Combinacion> tallasPorColor=Utilidades.tallasPorColor(producto.getCombinacions(),color.getColor());
                tallaSpinner.setAdapter(new TallaSpinnerAdapter(getActivity(),R.layout.item_talla,tallasPorColor));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tallaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Combinacion combinacion=(Combinacion)parent.getItemAtPosition(position);
                cantDisponiblesTV.setText(combinacion.getCantidad()+"");
                if(!combinacion.getIdImagenes().isEmpty()){
                    String IMAGE_URL = urlBase+"/"+producto.getId()+"/"+combinacion.getIdImagenes().get(0);
                    ImageLoader imageLoader= MySingletonClass.getInstance(getActivity().getApplicationContext(), usuario, contraseña).getImageLoader();
                    foto.setImageUrl(IMAGE_URL, imageLoader);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        añadirCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int cantR=Integer.parseInt(cantReservarET.getText().toString());
                    if(cantR>0 && cantR<=((Combinacion)tallaSpinner.getSelectedItem()).getCantidad()){
                        onButtonPressed(Utilidades.idModificarCesta,tallaSpinner.getSelectedItem(),cantR);
                    }else{
                        Toast.makeText(getActivity(), "Cantidad fuera de los limites", Toast.LENGTH_SHORT).show();
                    }
                }catch (NumberFormatException ex){
                    Toast.makeText(getActivity(), "Cantidad a reservar erronea", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(Utilidades.idModificarCesta,tallaSpinner.getSelectedItem(),0);
            }
        });

    }

    public void onButtonPressed(int idFragment, Object ... dato) {
        if (comm != null) {
            comm.onFragmentIteration(idFragment, dato);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            comm = (FragmentIterationListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        comm = null;
    }



}
