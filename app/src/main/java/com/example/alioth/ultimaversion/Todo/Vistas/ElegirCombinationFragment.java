package com.example.alioth.ultimaversion.Todo.Vistas;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Adapter.ColorSpinnerAdapter;
import com.example.alioth.ultimaversion.Todo.Modelo.Producto;
import com.example.alioth.ultimaversion.Todo.Util.FragmentIterationListener;
import com.example.alioth.ultimaversion.Todo.Util.Utilidades;
import com.squareup.okhttp.internal.Util;


public class ElegirCombinationFragment extends Fragment {
    private static final String PRODUCTO = "PRODUCTO";
    private Producto producto;
    private Spinner tallaSpinner;
    private Spinner colorSpinner;
    private TextView cantDisponiblesTV;
    private EditText cantReservarET;


    private FragmentIterationListener comm;

    public static ElegirCombinationFragment newInstance(Producto producto) {
        ElegirCombinationFragment fragment = new ElegirCombinationFragment();
        Bundle args = new Bundle();
        args.putSerializable(PRODUCTO, producto);
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
            producto = (Producto)getArguments().getSerializable(PRODUCTO);
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
        colorSpinner.setAdapter(new ColorSpinnerAdapter(getActivity(),R.layout.item_color,Utilidades.colores(producto.getCombinacions())));
        this.tallaSpinner=(Spinner)root.findViewById(R.id.spinner3);
        this.cantDisponiblesTV=(TextView)root.findViewById(R.id.textView19);
        this.cantReservarET=(EditText)root.findViewById(R.id.editText4);
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
