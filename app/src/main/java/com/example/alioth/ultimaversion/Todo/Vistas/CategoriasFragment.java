package com.example.alioth.ultimaversion.Todo.Vistas;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Adapter.CategoriasAdapter;
import com.example.alioth.ultimaversion.Todo.Modelo.Categoria;
import com.example.alioth.ultimaversion.Todo.Util.FragmentIterationListener;
import com.example.alioth.ultimaversion.Todo.Util.Utilidades;

import java.io.Serializable;
import java.util.Map;


public class CategoriasFragment extends Fragment {
    private FragmentIterationListener comm;
    private Map<Integer, Categoria> categoriaMap;
    private ListView categoriaListView;

    public static CategoriasFragment newInstance(Map<Integer, Categoria> categoriaMap) {
        CategoriasFragment fragment = new CategoriasFragment();
        Bundle args = new Bundle();
        args.putSerializable("categoriaMap", (java.io.Serializable) categoriaMap);
        fragment.setArguments(args);
        return fragment;
    }

    public CategoriasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoriaMap = (Map<Integer, Categoria>) getArguments().getSerializable("categoriaMap");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_categorias,null);
        init(root);
        return root;
    }

    private void init(ViewGroup root) {
        categoriaListView=(ListView)root.findViewById(R.id.lv_categorias);
        categoriaListView.setAdapter(new CategoriasAdapter(categoriaMap.values(),getActivity(),R.layout.item_categoria));
        categoriaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onButtonPressed(Utilidades.idFragmentProducto, parent.getAdapter().getItem(position));
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
