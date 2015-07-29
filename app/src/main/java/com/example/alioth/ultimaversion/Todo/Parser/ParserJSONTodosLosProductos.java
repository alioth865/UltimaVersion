package com.example.alioth.ultimaversion.Todo.Parser;

import com.example.alioth.ultimaversion.Todo.Modelo.Combinacion;
import com.example.alioth.ultimaversion.Todo.Modelo.Producto;
import com.example.alioth.ultimaversion.Todo.Util.Utilidades;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alioth on 16/06/2015.
 */
public class ParserJSONTodosLosProductos {
    private JSONObject todosProductosJSON;
    Map<String, String> configuracion;
    Map<Integer, Combinacion> combinaciones;

    public ParserJSONTodosLosProductos(String todosProductosJSON, Map<String, String> configuracion, Map<Integer, Combinacion> combinaciones) throws JSONException {
        this.todosProductosJSON = new JSONObject(todosProductosJSON);
        this.configuracion=configuracion;
        this.combinaciones = combinaciones;
    }

    public Map<Integer, Producto> todasLosProductos() throws JSONException, IOException {
        JSONArray jsonArray= todosProductosJSON.getJSONArray("products");
        Map<Integer, Producto> map=new HashMap<>();
        for(int i=0;i<jsonArray.length();i++){
            int id=jsonArray.getJSONObject(i).getInt("id");
            ParserJSONProductoEspecifico parserJSONProductoEspecifico=new ParserJSONProductoEspecifico(jsonArray.getJSONObject(i), combinaciones,configuracion);
            map.put(id, parserJSONProductoEspecifico.productoEspecifico());
        }
        return map;
    }
}

