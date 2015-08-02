package com.example.alioth.ultimaversion.Todo.Vistas;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Adapter.ProductosAdapter;
import com.example.alioth.ultimaversion.Todo.Modelo.Categoria;
import com.example.alioth.ultimaversion.Todo.Util.FragmentIterationListener;
import com.example.alioth.ultimaversion.Todo.Util.Utilidades;

import java.io.Serializable;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductosFragment extends Fragment  {
    private FragmentIterationListener comm;
    private Categoria categoriaEspecifica;
    private ListView productosListView;
    private Map<String,String> configuracion;

    public ProductosFragment() {
        // Required empty public constructor
    }

    public static ProductosFragment newInstance(Categoria categoriaEspecifica, Map<String,String> configuracion) {
        ProductosFragment fragment = new ProductosFragment();
        Bundle args = new Bundle();
        args.putSerializable("categoriaEspecifica", (java.io.Serializable) categoriaEspecifica);
        args.putSerializable("configuracion", (Serializable)configuracion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoriaEspecifica = (Categoria) getArguments().getSerializable("categoriaEspecifica");
            configuracion=(Map<String,String>) getArguments().getSerializable("configuracion");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_productos,null);
        init(root);
        return root;
    }

    private void init(ViewGroup root) {
        productosListView=(ListView)root.findViewById(R.id.lv_productos);
        productosListView.setAdapter(new ProductosAdapter(categoriaEspecifica.getProductos(),getActivity(),R.layout.item_producto,configuracion));
        productosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onButtonPressed(Utilidades.idFragmentProductoEspecifico, parent.getAdapter().getItem(position));
            }
        });
    }


    public void onButtonPressed(int idFragment, Object dato) {
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
