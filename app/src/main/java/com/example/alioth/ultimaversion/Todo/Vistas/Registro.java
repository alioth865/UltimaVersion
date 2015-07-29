package com.example.alioth.ultimaversion.Todo.Vistas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.ConditionVariable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Modelo.Categoria;
import com.example.alioth.ultimaversion.Todo.Modelo.Cliente;
import com.example.alioth.ultimaversion.Todo.Modelo.Combinacion;
import com.example.alioth.ultimaversion.Todo.Modelo.Empleado;
import com.example.alioth.ultimaversion.Todo.Modelo.OpcionesProductos;
import com.example.alioth.ultimaversion.Todo.Modelo.Producto;
import com.example.alioth.ultimaversion.Todo.Parser.ParserJSONTodasCategorias;
import com.example.alioth.ultimaversion.Todo.Parser.ParserJSONTodasCombinaciones;
import com.example.alioth.ultimaversion.Todo.Parser.ParserJSONTodosClientes;
import com.example.alioth.ultimaversion.Todo.Parser.ParserJSONTodosEmployees;
import com.example.alioth.ultimaversion.Todo.Parser.ParserJSONTodosLosProductos;
import com.example.alioth.ultimaversion.Todo.Parser.ParserJSONTodosOpcionesProductos;
import com.example.alioth.ultimaversion.Todo.Util.Utilidades;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;


public class Registro extends ActionBarActivity {
    private EditText usuario;
    private EditText contraseña;
    private Button aceptar;
    private boolean terminoCarga=false;
    private ConditionVariable condicionCarga=new ConditionVariable();
    private Map<String,String> configuracion;
    public static final String MAP_TODAS_LAS_CATEGORIAS="MAP_TODAS_LAS_CATEGORIAS";
    public static final String MAP_TODOS_LOS_CLIENTES="MAP_TODOS_LOS_CLIENTES";
    public static final String CONFIGURACION="CONFIGURACION";
    Map<Integer, Cliente> mapTodosLosClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        inicializarComponente();
    }

    private void inicializarComponente() {
        configuracion= Utilidades.Config(getResources());
        usuario=(EditText)findViewById(R.id.editText);
        contraseña=(EditText)findViewById(R.id.editText2);
        aceptar=(Button)findViewById(R.id.button);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aceptar.setEnabled(false);
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    String urlEmpleados="http://"+configuracion.get("shop_url")+"/"+configuracion.get("name_shop")+"/api/employees/?display=full&output_format=JSON";
                    new HiloProductos().execute(urlEmpleados, usuario.getText().toString(), configuracion.get("cookie_key") + contraseña.getText().toString());
                    new HiloClientes().execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Problemas de conexión",Toast.LENGTH_LONG).show();
                    aceptar.setEnabled(false);
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro, menu);
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

    class HiloProductos extends AsyncTask<String,Boolean,Void>{
        boolean autenticacioAceptada=false;


        //TODO ELIMINAR DE AQUI
        Map<Integer, OpcionesProductos> mapTodasOpcionesProductos;
        Map<Integer, Combinacion> mapTodasLasCombinaciones;
        Map<Integer, Producto> mapTodosLosProductos;
        Map<Integer, Categoria> mapTodasLasCategorias;


        @Override
        protected void onProgressUpdate(Boolean... values) {

                ProgressDialog.show(
                        Registro.this
                        , "Entrando"
                        , "Cargando los datos"
                        , true
                        , false);

        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                InputStream inputStream= Utilidades.peticionHttp(configuracion.get("key"), "", params[0]);
                String s= Utilidades.getStringFromInputStream(inputStream);
                ParserJSONTodosEmployees parser=new ParserJSONTodosEmployees(s);
                Map<Integer,Empleado> todosEmpleados=parser.todosEmpleados();
                String usu=params[1];
                String pwd=params[2];
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.update(pwd.getBytes());
                byte[] messageDigest = digest.digest();
                String contraseñaencryptada = "";
                for (int i=0; i<messageDigest.length; i++){
                    contraseñaencryptada+=Integer.toHexString((messageDigest[i]>>4) & 0xf );
                    contraseñaencryptada+= Integer.toHexString(messageDigest[i] & 0xf);
                }
                for(Empleado e:todosEmpleados.values()){
                    if(e.getEmail().equals(usu) && e.getContraseña().equals(contraseñaencryptada)){
                        autenticacioAceptada=true;
                        break;
                    }
                }
                /*
                for(int i=0;i<idEmpleados.size() && !autenticacioAceptada;i++){
                    String urlEmpleadoEspecifico="http://"+configuracion.get("shop_url")+"/"+configuracion.get("name_shop")+"/api/employees/"+idEmpleados.get(i)+"?output_format=JSON";
                    inputStream= Utilidades.peticionHttp(configuracion.get("key"), "", urlEmpleadoEspecifico);
                    s= Utilidades.getStringFromInputStream(inputStream);
                    Empleado e=new ParserJSONEmpleadoEspecifico(s).getEmpleado();
                    if(e.getEmail().equals(usu) && e.getContraseña().equals(contraseñaencryptada)){
                        autenticacioAceptada=true;
                        break;
                    }
                }*/

                if(autenticacioAceptada){
                    publishProgress(true);
                    String url="http://"+configuracion.get("shop_url")+"/"+configuracion.get("name_shop")+"/api/product_option_values/?display=full&output_format=JSON";
                    inputStream=Utilidades.peticionHttp(configuracion.get("key"), "", url);
                    s= Utilidades.getStringFromInputStream(inputStream);
                    mapTodasOpcionesProductos=new ParserJSONTodosOpcionesProductos(s,configuracion).todasLasOpciones();


                    url="http://"+configuracion.get("shop_url")+"/"+configuracion.get("name_shop")+"/api/combinations/?display=full&output_format=JSON";
                    inputStream=Utilidades.peticionHttp(configuracion.get("key"), "", url);
                    s= Utilidades.getStringFromInputStream(inputStream);
                    mapTodasLasCombinaciones=new ParserJSONTodasCombinaciones(s,configuracion, mapTodasOpcionesProductos).todasLasCombinaciones();


                    url="http://"+configuracion.get("shop_url")+"/"+configuracion.get("name_shop")+"/api/products/?display=full&output_format=JSON";
                    inputStream=Utilidades.peticionHttp(configuracion.get("key"), "", url);
                    s= Utilidades.getStringFromInputStream(inputStream);
                    mapTodosLosProductos=new ParserJSONTodosLosProductos(s,configuracion, mapTodasLasCombinaciones).todasLosProductos();


                    url="http://"+configuracion.get("shop_url")+"/"+configuracion.get("name_shop")+"/api/categories/?display=full&output_format=JSON";
                    inputStream=Utilidades.peticionHttp(configuracion.get("key"), "", url);
                    s= Utilidades.getStringFromInputStream(inputStream);
                    mapTodasLasCategorias=new ParserJSONTodasCategorias(s,configuracion, mapTodosLosProductos).todasLasCategorias();


                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(autenticacioAceptada){
                while(!terminoCarga) {
                    condicionCarga.block();
                }
                Intent i = new Intent(Registro.this, Principal.class);
                i.putExtra(MAP_TODAS_LAS_CATEGORIAS, (java.io.Serializable) mapTodasLasCategorias);
                i.putExtra(MAP_TODOS_LOS_CLIENTES, (java.io.Serializable) mapTodosLosClientes);
                i.putExtra(CONFIGURACION, (java.io.Serializable) configuracion);
                startActivity(i);

                /*Toast.makeText(getApplicationContext(), "Autenticacion aceptada: "+ mapTodasOpcionesProductos.values().size()+", "
                        +mapTodasLasCombinaciones.values().size()+", "
                        +mapTodosLosProductos.values().size()+", "
                        +mapTodasLasCategorias.values().size(), Toast.LENGTH_SHORT).show()*/
            }else{
                Toast.makeText(getApplicationContext(), "Usuario o contraseña no válidas", Toast.LENGTH_SHORT).show();
                aceptar.setEnabled(true);
            }
        }
    }

    public class HiloClientes extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... params) {
            String url="http://"+configuracion.get("shop_url")+"/"+configuracion.get("name_shop")+"/api/customers/?display=full&output_format=JSON";
            InputStream inputStream= null;
            try {
                inputStream = Utilidades.peticionHttp(configuracion.get("key"), "", url);
                String s= Utilidades.getStringFromInputStream(inputStream);
                mapTodosLosClientes=new ParserJSONTodosClientes(s,configuracion).todosClientes();
                terminoCarga=Boolean.TRUE;
                condicionCarga.open();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            terminoCarga=true;
        }
    }
}
