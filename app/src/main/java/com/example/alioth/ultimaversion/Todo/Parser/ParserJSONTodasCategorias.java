package com.example.alioth.ultimaversion.Todo.Parser;

import com.example.alioth.ultimaversion.Todo.Modelo.Categoria;
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
public class ParserJSONTodasCategorias {
    private JSONObject todasCategoriasJSON;
    Map<String, String> configuracion;
    Map<Integer, Producto> productos;

    public ParserJSONTodasCategorias(String todasCategoriasJSON, Map<String, String> configuracion, Map<Integer, Producto> productos) throws JSONException {
        this.todasCategoriasJSON = new JSONObject(todasCategoriasJSON);
        this.configuracion=configuracion;
        this.productos = productos;
    }

    public Map<Integer, Categoria> todasLasCategorias() throws JSONException, IOException {
        JSONArray jsonArray= todasCategoriasJSON.getJSONArray("categories");
        Map<Integer, Categoria> map=new HashMap<>();
        for(int i=0;i<jsonArray.length();i++){
            int id=jsonArray.getJSONObject(i).getInt("id");
            ParserJSONCategoriaEspecifica parserJSONCategoriaEspecifica=new ParserJSONCategoriaEspecifica(jsonArray.getJSONObject(i), productos,configuracion);
            map.put(id, parserJSONCategoriaEspecifica.categoriaEspecifica());
        }
        return map;
    }
}
