package com.example.alioth.ultimaversion.Todo.Vistas;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;

import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Adapter.BuscadorAdapter;
import com.example.alioth.ultimaversion.Todo.Modelo.Categoria;
import com.example.alioth.ultimaversion.Todo.Modelo.Producto;
import com.example.alioth.ultimaversion.Todo.Util.FragmentIterationListener;
import com.example.alioth.ultimaversion.Todo.Util.Utilidades;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class BuscarProductoFragment extends Fragment {
    private static final String TODOS_PRODUCTOS = "todosProductos";
    private Map<Integer, Categoria> todosProductos;
    private EditText etBuscador;
    private ListView lvResultado;
    private BuscadorAdapter adapter;

    private FragmentIterationListener comm;


    public static BuscarProductoFragment newInstance(Map<Integer, Categoria> todosProductos) {
        BuscarProductoFragment fragment = new BuscarProductoFragment();
        Bundle args = new Bundle();
        args.putSerializable(TODOS_PRODUCTOS, (java.io.Serializable) todosProductos);
        fragment.setArguments(args);
        return fragment;
    }

    public BuscarProductoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            todosProductos = (Map<Integer, Categoria>) getArguments().getSerializable(TODOS_PRODUCTOS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_buscar_producto, container, false);
        init(root);
        return root;
    }

    private void init(View root) {
        etBuscador =(EditText)root.findViewById(R.id.editText3);
        lvResultado=(ListView)root.findViewById(R.id.listView3);
        Map<String,String> configuracion= Utilidades.Config(getResources());
        adapter=new BuscadorAdapter(todosProductos,getActivity(),R.layout.item_producto_buscar,configuracion);
        lvResultado.setAdapter(adapter);
        etBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((Filterable)(adapter)).getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

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
