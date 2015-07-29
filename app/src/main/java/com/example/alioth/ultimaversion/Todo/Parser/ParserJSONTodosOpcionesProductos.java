package com.example.alioth.ultimaversion.Todo.Parser;

import com.example.alioth.ultimaversion.Todo.Modelo.OpcionesProductos;
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
public class ParserJSONTodosOpcionesProductos {
    private JSONObject todasOpcionesProductosJSON;
    Map<String, String> configuracion;

    public ParserJSONTodosOpcionesProductos(String todasOpcionesProductosJSON, Map<String, String> configuracion) throws JSONException {
        this.todasOpcionesProductosJSON = new JSONObject(todasOpcionesProductosJSON);
        this.configuracion=configuracion;
    }



    public Map<Integer, OpcionesProductos> todasLasOpciones() throws JSONException, IOException {
        JSONArray jsonArray=todasOpcionesProductosJSON.getJSONArray("product_option_values");
        Map<Integer, OpcionesProductos> map=new HashMap<>();
        for(int i=0;i<jsonArray.length();i++){
            int id=jsonArray.getJSONObject(i).getInt("id");
            ParserJSONOpcionesProductoEspecifico parserJSONOpcionesProductoEspecifico=new ParserJSONOpcionesProductoEspecifico(jsonArray.getJSONObject(i),Integer.parseInt(configuracion.get("id_language")));
            map.put(id,parserJSONOpcionesProductoEspecifico.getEmpleado());
        }
        return map;
    }
}
