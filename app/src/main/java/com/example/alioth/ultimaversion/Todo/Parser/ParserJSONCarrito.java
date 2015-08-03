package com.example.alioth.ultimaversion.Todo.Parser;

import com.example.alioth.ultimaversion.Todo.Modelo.Producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AliothAntonio on 31/07/2015.
 */
public class ParserJSONCarrito {
    JSONObject carritoJSON;

    /*public ParserJSONCategoriaEspecifica(String jsonObject, Map<Integer, Producto> map,  Map<String, String> configuracion) throws JSONException {
        this.jsonObject = new JSONObject(jsonObject);
        this.map = map;
        this.configuracion=configuracion;
    }*/

    public ParserJSONCarrito(String jsonObject) throws JSONException {
        this.carritoJSON = new JSONObject(jsonObject);
    }

    public int idCarrito() throws JSONException {
        //JSONObject categoriaJSON=jsonObject.getJSONObject("category");
        int id = carritoJSON.getJSONObject("cart").getInt("id");
        return id;
    }
}
