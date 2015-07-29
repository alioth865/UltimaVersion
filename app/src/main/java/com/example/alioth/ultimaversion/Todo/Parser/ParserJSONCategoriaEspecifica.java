package com.example.alioth.ultimaversion.Todo.Parser;

import com.example.alioth.ultimaversion.Todo.Modelo.Categoria;
import com.example.alioth.ultimaversion.Todo.Modelo.Producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alioth on 16/06/2015.
 */
public class ParserJSONCategoriaEspecifica {
    JSONObject categoriaJSON;
    Map<Integer, Producto> map;
    Map<String, String> configuracion;

    /*public ParserJSONCategoriaEspecifica(String jsonObject, Map<Integer, Producto> map,  Map<String, String> configuracion) throws JSONException {
        this.jsonObject = new JSONObject(jsonObject);
        this.map = map;
        this.configuracion=configuracion;
    }*/

    public ParserJSONCategoriaEspecifica(JSONObject jsonObject, Map<Integer, Producto> map, Map<String, String> configuracion) {
        this.categoriaJSON = jsonObject;
        this.map = map;
        this.configuracion = configuracion;
    }

    public Categoria categoriaEspecifica() throws JSONException {
        //JSONObject categoriaJSON=jsonObject.getJSONObject("category");
        int id=categoriaJSON.getInt("id");
        JSONArray arrayName=categoriaJSON.getJSONArray("name");
        int id_language=Integer.parseInt(configuracion.get("id_language"));
        String name="";
        for (int i = 0; i < arrayName.length(); i++) {
            if(id_language==arrayName.getJSONObject(i).getInt("id")){
                name=arrayName.getJSONObject(i).getString("value");
            }
        }
        List<Producto> productos=new LinkedList<>();
        try {
            JSONArray arrayProductos=categoriaJSON.getJSONObject("associations").getJSONArray("products");
            for (int i = 0; i < arrayProductos.length(); i++) {
                productos.add(map.get(arrayProductos.getJSONObject(i).getInt("id")));
            }
        }catch (JSONException exec){

        }
        return new Categoria(id, name, productos);
    }
}
