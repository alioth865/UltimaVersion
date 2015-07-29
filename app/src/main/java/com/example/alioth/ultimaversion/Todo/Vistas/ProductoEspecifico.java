package com.example.alioth.ultimaversion.Todo.Vistas;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Adapter.MyFragmentPagerAdapter;
import com.example.alioth.ultimaversion.Todo.Modelo.Combinacion;
import com.example.alioth.ultimaversion.Todo.Modelo.Producto;
import com.example.alioth.ultimaversion.Todo.Util.FragmentIterationListener;

import java.util.Map;


public class ProductoEspecifico extends FragmentActivity {
    public static final String PRODUCTO = "PRODUCTO";
    public static final String CONFIGURACION = "CONFIGURACION";
    private Producto producto;
    private Map<String, String> configuracion;
    private FragmentIterationListener comm;
    private ViewPager viewPager;
    private TextView nombre;
    private TextView referencia;
    private TextView descripcion;
    private TextView precio;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fragment_producto_especifico);
        producto= (Producto) getIntent().getSerializableExtra(PRODUCTO);
        configuracion=(Map<String,String>)getIntent().getSerializableExtra(CONFIGURACION);
        // Instantiate a Views
        this.nombre=(TextView)findViewById(R.id.textView3);
        this.referencia=(TextView)findViewById(R.id.textView4);
        this.descripcion=(TextView)findViewById(R.id.textView5);
        this.precio=(TextView)findViewById(R.id.textView6);
        this.viewPager=(ViewPager)findViewById(R.id.viewpager);
        //Dando valores
        this.nombre.setText(producto.getNombre());
        this.descripcion.setText(producto.getDescripcion());
        this.precio.setText(producto.getPrecio()+"");
        this.referencia.setText(producto.getReferencia());
        // Create an adapter with the fragments we show on the ViewPager
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        for(int idImagen:producto.getIdImagenes()){
            adapter.addFragment(VistaImagenFragment.newInstance(idImagen,producto.getId(),configuracion));
        }
        this.viewPager.setAdapter(adapter);
    }


    /* public static ProductoEspecifico newInstance(Producto producto, Map<String, String> configuracion) {
        ProductoEspecifico fragment = new ProductoEspecifico();
        Bundle args = new Bundle();
        args.putSerializable(PRODUCTO, producto);
        args.putSerializable(CONFIGURACION, (java.io.Serializable) configuracion);
        fragment.setArguments(args);
        return fragment;
    }

    public ProductoEspecifico() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            producto = (Producto) getArguments().getSerializable(PRODUCTO);
            configuracion = (Map<String, String>) getArguments().getSerializable(CONFIGURACION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_producto_especifico, null);
        init(root);
        return root;
    }

    private void init(ViewGroup root) {
        this.nombre=(TextView)root.findViewById(R.id.textView3);
        this.referencia=(TextView)root.findViewById(R.id.textView4);
        this.descripcion=(TextView)root.findViewById(R.id.textView5);
        this.precio=(TextView)root.findViewById(R.id.textView6);
        this.viewPager=(ViewPager)root.findViewById(R.id.pager);
        MyFragmentPagerAdapter adapter=new MyFragmentPagerAdapter();

    }


    public void onButtonPressed(int idFragment, Object object) {
        if (comm != null) {
            comm.onFragmentIteration(idFragment, object);
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

    */


}
