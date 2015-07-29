package com.example.alioth.ultimaversion.Todo.Parser;

import com.example.alioth.ultimaversion.Todo.Modelo.Combinacion;
import com.example.alioth.ultimaversion.Todo.Modelo.OpcionesProductos;
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
public class ParserJSONProductoEspecifico {
    JSONObject productoJSON;
    Map<Integer, Combinacion> todasCombinaciones;
    Map<String, String> configuracion;

    /*public ParserJSONProductoEspecifico(String jsonObject, Map<Integer, Combinacion> todasCombinaciones, Map<String, String> configuracion) throws JSONException {
        this.jsonObject = new JSONObject(jsonObject);
        this.todasCombinaciones = todasCombinaciones;
        this.configuracion=configuracion;
    }*/

    public ParserJSONProductoEspecifico(JSONObject jsonObject, Map<Integer, Combinacion> todasCombinaciones, Map<String, String> configuracion) {
        this.productoJSON = jsonObject;
        this.todasCombinaciones = todasCombinaciones;
        this.configuracion = configuracion;
    }

    public Producto productoEspecifico() throws JSONException {
        //JSONObject productoJSON=jsonObject.getJSONObject("product");
        int id=productoJSON.getInt("id");
        int idDefaultImage=productoJSON.getInt("id_default_image");
        double precio=productoJSON.getDouble("price");
        String referencia=productoJSON.getString("reference");
        int id_language=Integer.parseInt(configuracion.get("id_language"));
        JSONArray arrayDescripcion=productoJSON.getJSONArray("description");
        String descripcion="";
        for (int i = 0; i < arrayDescripcion.length(); i++) {
            if(id_language==arrayDescripcion.getJSONObject(i).getInt("id")){
                descripcion=arrayDescripcion.getJSONObject(i).getString("value");
            }
        }
        JSONArray arrayNombre=productoJSON.getJSONArray("name");
        String nombre="";
        for (int i = 0; i < arrayNombre.length(); i++) {
            if(id_language==arrayNombre.getJSONObject(i).getInt("id")){
                nombre=arrayNombre.getJSONObject(i).getString("value");
            }
        }
        JSONArray arrayCombinations=productoJSON.getJSONObject("associations").getJSONArray("combinations");
        List<Combinacion> opcionesProductos=new LinkedList<>();
        for (int i = 0; i < arrayCombinations.length(); i++) {
            opcionesProductos.add(todasCombinaciones.get(arrayCombinations.getJSONObject(i).getInt("id")));
        }
        JSONArray arrayImagenes=productoJSON.getJSONObject("associations").getJSONArray("images");
        List<Integer> imagenes=new LinkedList<>();
        for(int i = 0; i < arrayImagenes.length(); i++) {
            imagenes.add(arrayImagenes.getJSONObject(i).getInt("id"));
        }
        return new Producto(id, idDefaultImage, precio, referencia, descripcion, nombre,  opcionesProductos, imagenes);
    }
}
