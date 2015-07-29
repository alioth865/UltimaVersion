package com.example.alioth.ultimaversion.Todo.Vistas;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Adapter.ClientesApater;
import com.example.alioth.ultimaversion.Todo.Modelo.Categoria;
import com.example.alioth.ultimaversion.Todo.Modelo.Cliente;
import com.example.alioth.ultimaversion.Todo.Util.FragmentIterationListener;

import java.util.Map;

public class TodosClientesFragment extends Fragment {

    private static final String MAP_TODOS_CLIENTES = "MAP_TODOS_CLIENTES";
    private static final String MAP_TODOS_PRODUCTOS = "MAP_TODOS_PRODUCTOS";
    private Map<Integer,Cliente> todosClientesMap;
    private Map<Integer,Categoria> todasCategoriasMap;
    private ListView todoClientesListView;

    private FragmentIterationListener comm;

    public static TodosClientesFragment newInstance(Map<Integer,Cliente> todosClientesMap, Map<Integer,Categoria> todasCategoriasMap) {
        TodosClientesFragment fragment = new TodosClientesFragment();
        Bundle args = new Bundle();
        args.putSerializable(MAP_TODOS_CLIENTES, (java.io.Serializable) todosClientesMap);
        args.putSerializable(MAP_TODOS_PRODUCTOS,(java.io.Serializable) todasCategoriasMap);
        fragment.setArguments(args);
        return fragment;
    }

    public TodosClientesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            todosClientesMap = (Map<Integer, Cliente>) getArguments().getSerializable(MAP_TODOS_CLIENTES);
            todasCategoriasMap=(Map<Integer,Categoria>)getArguments().getSerializable(MAP_TODOS_PRODUCTOS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_todos_clientes, container, false);
        init(root);
        return root;
    }

    private void init(View root) {
        this.todoClientesListView=(ListView)root.findViewById(R.id.listView);
        todoClientesListView.setAdapter(new ClientesApater(todosClientesMap.values(), todasCategoriasMap, getActivity()));
    }

    // TODO: Rename method, update argument and hook method into UI event
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
