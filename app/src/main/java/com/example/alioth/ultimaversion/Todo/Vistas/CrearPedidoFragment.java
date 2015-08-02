package com.example.alioth.ultimaversion.Todo.Vistas;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Adapter.CestaAdapter;
import com.example.alioth.ultimaversion.Todo.Modelo.Categoria;
import com.example.alioth.ultimaversion.Todo.Modelo.Cesta;
import com.example.alioth.ultimaversion.Todo.Modelo.Cliente;
import com.example.alioth.ultimaversion.Todo.Modelo.Direccion;
import com.example.alioth.ultimaversion.Todo.Parser.ParserJSONCarrito;
import com.example.alioth.ultimaversion.Todo.Util.FragmentIterationListener;
import com.example.alioth.ultimaversion.Todo.Util.Utilidades;

import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;


public class CrearPedidoFragment extends Fragment {

    private static final String TODOS_PRODUCTOS = "TODOS_PRODUCTOS";
    private static final String CLIENTE = "CLIENTE";
    private static final String CESTA = "CESTA";
    private static final String CONFIGURACION="CONFIGURACION";

    private Map<Integer,Categoria> todosProductos;
    private Cliente cliente;
    private FragmentIterationListener comm;
    private Direccion entrega;
    private Direccion factura;
    private Map<String, String> configuracion;
    //Elementos de las vistas
    private TextView tvNombreCliente;
    private ListView lvCarrito;
    private Button btnFinalizar;
    private Button btnCancelar;
    private ImageButton btnAñadirCarrito;
    private Spinner spinnerFactura;
    private Spinner spinnerEntrega;
    private Cesta cesta;


    public static CrearPedidoFragment newInstance(Map<Integer,Categoria> todosProductos, Cliente cliente, Cesta cesta, Map<String, String> configuracion) {
        CrearPedidoFragment fragment = new CrearPedidoFragment();
        Bundle args = new Bundle();
        args.putSerializable(TODOS_PRODUCTOS, (java.io.Serializable) todosProductos);
        args.putSerializable(CLIENTE, cliente);
        args.putSerializable(CESTA,cesta);
        args.putSerializable(CONFIGURACION, (java.io.Serializable) configuracion);
        fragment.setArguments(args);
        return fragment;
    }

    public CrearPedidoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            todosProductos = (Map<Integer, Categoria>) getArguments().getSerializable(TODOS_PRODUCTOS);
            cliente = (Cliente) getArguments().getSerializable(CLIENTE);
            cesta =(Cesta) getArguments().getSerializable(CESTA);
            configuracion=(Map<String,String>) getArguments().getSerializable(CONFIGURACION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_crear_pedido, container, false);
        init(root);
        return root;
    }

    private void init(View root) {
        tvNombreCliente=(TextView)root.findViewById(R.id.textView11);
        lvCarrito=(ListView)root.findViewById(R.id.listView2);
        btnFinalizar=(Button)root.findViewById(R.id.button2);
        btnCancelar=(Button)root.findViewById(R.id.button3);
        btnAñadirCarrito=(ImageButton)root.findViewById(R.id.imageButton2);;
        spinnerFactura=(Spinner)root.findViewById(R.id.spinner);
        spinnerEntrega=(Spinner)root.findViewById(R.id.spinner2);

        tvNombreCliente.setText(cliente.getFirstname()+" "+cliente.getLastname());
        ArrayAdapter<Direccion> adapter=new ArrayAdapter<Direccion>(getActivity(),android.R.layout.simple_spinner_item,cliente.getDirecciones());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEntrega.setAdapter(adapter);
        spinnerEntrega.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entrega=((Direccion)parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerFactura.setAdapter(adapter);
        spinnerFactura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                factura = ((Direccion) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAñadirCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(Utilidades.idFragmentBuscarProducto,todosProductos);
            }
        });
        lvCarrito.setAdapter(new CestaAdapter(cesta,getActivity(),R.layout.item_cesta, Utilidades.todosProductos(todosProductos)));


        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(cesta.getCesta().size()>0){
                        String xmlCetsta=Utilidades.creaCestaXML(cesta,entrega,factura,1,1);
                        new HiloCrearPedido(xmlCetsta).execute();

                    }
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(Utilidades.idFragmentClientes,null);
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


    public Cesta getCesta() {
        return cesta;
    }

    public class HiloCrearPedido extends AsyncTask<Void,Void,Void>{
        String xmlCesta;

        public HiloCrearPedido(String xmlCesta) {
            this.xmlCesta = xmlCesta;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String responseCesta=Utilidades.peticionPostHttp(configuracion.get("key"), "", "http://192.168.1.47/redegal/api/carts/?output_format=JSON", xmlCesta);
                int idCesta=new ParserJSONCarrito(responseCesta).idCarrito();
                String xmlPedido=Utilidades.creaPedidoXML(idCesta,cesta, entrega, factura, 1, 1, cliente.getIdCliente(), 1,"bankwire", "Transferencia bancaria", (float) 1.0);
                //TODO ESTO TENGO QUE VERLO CON TIAGO
                Utilidades.peticionPostHttp(configuracion.get("key"), "", "http://192.168.1.47/redegal/api/orders/?output_format=JSON", xmlPedido);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
            return null;
        }


    }
}
