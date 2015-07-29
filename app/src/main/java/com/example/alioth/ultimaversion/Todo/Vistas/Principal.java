package com.example.alioth.ultimaversion.Todo.Vistas;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Modelo.Categoria;
import com.example.alioth.ultimaversion.Todo.Modelo.Cliente;
import com.example.alioth.ultimaversion.Todo.Modelo.Producto;
import com.example.alioth.ultimaversion.Todo.Util.FragmentIterationListener;
import com.example.alioth.ultimaversion.Todo.Util.Utilidades;

import java.util.Map;


public class Principal extends ActionBarActivity implements View.OnTouchListener, FragmentIterationListener {
    private ImageButton btnCliente, btnProducto;
    private Map<Integer,Categoria> categoriaMap;
    private Map<Integer, Cliente> clientesMap;
    private CategoriasFragment categoriasFragment;
    private ProductosFragment productosFragment;
    private CrearPedidoFragment crearPedidoFragment;
    private BuscarProductoFragment buscarProductoFragment;



    private ElegirCombinationFragment elegirCombinationFragment;


    private TodosClientesFragment todosClientesFragment;
    private Map<String,String> configuracion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        categoriaMap=(Map<Integer,Categoria>)getIntent().getSerializableExtra(Registro.MAP_TODAS_LAS_CATEGORIAS);
        configuracion=(Map<String,String> )getIntent().getSerializableExtra(Registro.CONFIGURACION);
        clientesMap=(Map<Integer, Cliente>)getIntent().getSerializableExtra(Registro.MAP_TODOS_LOS_CLIENTES);
        inicializarComponente();
    }

    private void inicializarComponente() {
        btnCliente=(ImageButton)findViewById(R.id.btn_cliente);
        btnCliente.setOnTouchListener(this);
        btnProducto=(ImageButton)findViewById(R.id.btn_producto);
        btnProducto.setOnTouchListener(this);
        cargarFragmento(getCategoriasFragment());
    }

    private void cargarFragmento(Fragment fragment) {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.contenedor, fragment);
        transaction.commit();
    }


    public CategoriasFragment getCategoriasFragment(){
        if(categoriasFragment==null) {
            categoriasFragment=CategoriasFragment.newInstance(categoriaMap);
        }
        return categoriasFragment;
    }

    public TodosClientesFragment getTodosClientesFragment() {
        if(todosClientesFragment==null) {
            todosClientesFragment=TodosClientesFragment.newInstance(clientesMap, categoriaMap);
        }
        return todosClientesFragment;
    }



    public ProductosFragment getProductosFragment(Categoria categoriaEspecifica) {
        if(productosFragment==null){
            productosFragment=ProductosFragment.newInstance(categoriaEspecifica,configuracion);
        }
        return productosFragment;
    }


    public CrearPedidoFragment getCrearPedidoFragment(Map<Integer,Categoria> todosProductos, Cliente c) {
        if(crearPedidoFragment==null){
            crearPedidoFragment=CrearPedidoFragment.newInstance(todosProductos,c);
        }
        return crearPedidoFragment;
    }

    public ElegirCombinationFragment getElegirCombinationFragment(Producto p) {
        if(elegirCombinationFragment==null){
            elegirCombinationFragment=ElegirCombinationFragment.newInstance(p);
        }
        return elegirCombinationFragment;
    }

    private Fragment getBuscarProductoFragment(Map<Integer, Categoria> todosProductos) {
        if(buscarProductoFragment==null)
            buscarProductoFragment=BuscarProductoFragment.newInstance(todosProductos);
        return buscarProductoFragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageButton btn=(ImageButton) v;
        int actionMasked=event.getActionMasked();
        switch (actionMasked){
            case MotionEvent.ACTION_DOWN:
                btn.setColorFilter(R.color.entintado_oscuro);
                btn.invalidate();
                cambiarFragmento(btn);
                break;
            case MotionEvent.ACTION_UP:
                btn.clearColorFilter();
                btn.invalidate();
                break;
        }
        return true;
    }

    private void cambiarFragmento(View view) {
        switch (view.getId()){
            case R.id.btn_cliente:
                cargarFragmento(getTodosClientesFragment());
                break;
            case R.id.btn_producto:
                cargarFragmento(getCategoriasFragment());
                break;
        }
    }


    @Override
    public void onFragmentIteration(int idFragment, Object ... dato) {
        Map<Integer,Categoria> todosProductos;
        switch (idFragment){
            case Utilidades.idFragmentProducto:
                cargarFragmento(getProductosFragment((Categoria)dato[0]));
                break;
            case Utilidades.idFragmentCategoria:
                break;
            case Utilidades.idFragmentProductoEspecifico:
                Producto p=(Producto) dato[0];
                Intent i=new Intent(Principal.this, ProductoEspecifico.class);
                i.putExtra(ProductoEspecifico.CONFIGURACION, (java.io.Serializable) configuracion);
                i.putExtra(ProductoEspecifico.PRODUCTO, (java.io.Serializable) p);
                startActivity(i);
                break;
            case Utilidades.idFragmentClientes:
                break;
            case Utilidades.idFragmentClienteEspecifico:
                break;
            case Utilidades.idFragmentCrearPedido:
                Cliente c=(Cliente)dato[0];
                todosProductos=(Map<Integer,Categoria>) dato[1];
                cargarFragmento(getCrearPedidoFragment(todosProductos, c));
                break;
            case Utilidades.idFragmentBuscarProducto:
                todosProductos=(Map<Integer,Categoria>) dato[0];
                cargarFragmento(getBuscarProductoFragment(todosProductos));
                break;
            case Utilidades.idFragmentElegirCombinacion:
                cargarFragmento(getElegirCombinationFragment((Producto) dato[0]));
                break;


        }
    }


}
