package com.example.alioth.ultimaversion.Todo.Parser;

import com.example.alioth.ultimaversion.Todo.Modelo.Cliente;
import com.example.alioth.ultimaversion.Todo.Util.Utilidades;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alioth on 26/06/2015.
 */
public class ParserJSONTodosClientes {
    private JSONObject todosClientesJSON;
    private Map<String, String> configuracion;

    public ParserJSONTodosClientes(String jsonString, Map<String, String> configuracion) throws JSONException {
        this.todosClientesJSON = new JSONObject(jsonString);
        this.configuracion=configuracion;
    }

    public Map<Integer, Cliente> todosClientes() throws JSONException, IOException {
        JSONArray jsonArray= todosClientesJSON.getJSONArray("customers");
        Map<Integer, Cliente> map=new HashMap<>();
        for(int i=0;i<jsonArray.length();i++){
            int id=jsonArray.getJSONObject(i).getInt("id");
            ParserJSONClienteEspecifico parserJSONClienteEspecifico=new ParserJSONClienteEspecifico(jsonArray.getJSONObject(i),configuracion);
            map.put(id, parserJSONClienteEspecifico.clienteEspecifico());
        }
        return map;
    }


}
