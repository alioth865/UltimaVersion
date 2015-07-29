package com.example.alioth.ultimaversion.Todo.Parser;

import com.example.alioth.ultimaversion.Todo.Modelo.Cliente;
import com.example.alioth.ultimaversion.Todo.Modelo.Direccion;
import com.example.alioth.ultimaversion.Todo.Modelo.TotalDeVentas;
import com.example.alioth.ultimaversion.Todo.Util.Utilidades;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alioth on 26/06/2015.
 */
public class ParserJSONClienteEspecifico {
    private JSONObject clienteJSON;
    private Map<String, String> configuracion;
    private String usuario;
    private String contraseña;

    public ParserJSONClienteEspecifico(JSONObject clienteJSON, Map<String, String> configuracion) {
        this.clienteJSON = clienteJSON;
        this.configuracion = configuracion;
        this.usuario=configuracion.get("key");
        this.contraseña="";
    }


    public Cliente clienteEspecifico() throws JSONException, IOException {
        //JSONObject clienteJSON = jsonObject.getJSONObject("customer");
        int id = clienteJSON.getInt("id");
        String firstname = clienteJSON.getString("firstname");
        String lastname = clienteJSON.getString("lastname");
        String email = clienteJSON.getString("email");
        String urlTotalVentas="http://"+configuracion.get("shop_url")+"/"+configuracion.get("name_shop")+"/api/orders/?display=[total_paid]&output_format=JSON&filter[id_customer]=["+id+"]&filter[valid]=[1]";
        String urlDirecciones="http://"+configuracion.get("shop_url")+"/"+configuracion.get("name_shop")+"/api/addresses/?display=full&output_format=JSON&&filter[id_customer]=["+id+"]";
        InputStream inputStream= Utilidades.peticionHttp(usuario,contraseña,urlDirecciones);
        String s=Utilidades.getStringFromInputStream(inputStream);
        List<Direccion> direccion=new LinkedList<>();
        if(!s.equals("[]")){
            direccion=new ParserJSONDirecciones(new JSONObject(s)).getDirecciones();
        }
        inputStream=Utilidades.peticionHttp(usuario,contraseña,urlTotalVentas);
        s=Utilidades.getStringFromInputStream(inputStream);
        TotalDeVentas totalDeVentas=new TotalDeVentas();
        if(!s.equals("[]")) {
            totalDeVentas = new ParserJSONTotalVentas(new JSONObject(s)).getTotalVentas();
        }
        return new Cliente(id, firstname, lastname, email, direccion,totalDeVentas);
    }
}
