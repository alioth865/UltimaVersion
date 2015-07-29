package com.example.alioth.ultimaversion.Todo.Parser;

import com.example.alioth.ultimaversion.Todo.Modelo.Combinacion;
import com.example.alioth.ultimaversion.Todo.Modelo.OpcionesProductos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alioth on 16/06/2015.
 */
public class ParserJSONCombinationEspecifica {
    JSONObject combinacionJson;
    Map<Integer, OpcionesProductos> map;

    /*public ParserJSONCombinationEspecifica(String jsonObject, Map<Integer, OpcionesProductos> map) throws JSONException {
        this.jsonObject = new JSONObject(jsonObject);
        this.map = map;
    }*/

    public ParserJSONCombinationEspecifica(JSONObject jsonObject, Map<Integer, OpcionesProductos> map) {
        this.combinacionJson = jsonObject;
        this.map = map;
    }

    public Combinacion combinacionEspecifica() throws JSONException {
        //JSONObject combinacionJson=jsonObject.getJSONObject("combination");
        Combinacion combinacion=new Combinacion();
        combinacion.setId(combinacionJson.getInt("id"));
        //combinacion.setCantidad(combinacionJson.getInt("quantity"));
        List<Integer> imagenes=new LinkedList<>();
        List<OpcionesProductos> opciones=new LinkedList<>();
        try{
            JSONArray jsonArrayOpciones=combinacionJson.getJSONObject("associations").getJSONArray("product_option_values");
            for (int i = 0; i < jsonArrayOpciones.length(); i++) {
                opciones.add(map.get(jsonArrayOpciones.getJSONObject(i).getInt("id")));
            }
        }catch(JSONException e){

        }finally {
            combinacion.setOpcionesProductosList(opciones);
        }
        try{
            JSONArray jsonArrayImagenes=combinacionJson.getJSONObject("associations").getJSONArray("images");
            for (int i = 0; i < jsonArrayImagenes.length(); i++) {
                imagenes.add(jsonArrayImagenes.getJSONObject(i).getInt("id"));
            }

        }catch(JSONException e){

        }finally {
            combinacion.setIdImagenes(imagenes);
        }

        return combinacion;
    }
}
