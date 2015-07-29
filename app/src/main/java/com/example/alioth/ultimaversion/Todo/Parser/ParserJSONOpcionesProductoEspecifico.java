package com.example.alioth.ultimaversion.Todo.Parser;

import com.example.alioth.ultimaversion.Todo.Modelo.Color;
import com.example.alioth.ultimaversion.Todo.Modelo.OpcionesProductos;
import com.example.alioth.ultimaversion.Todo.Modelo.Talla;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alioth on 16/06/2015.
 */
public class ParserJSONOpcionesProductoEspecifico {
    private JSONObject opcionesProductosJSON;
    private int idLanguage;

    /*public ParserJSONOpcionesProductoEspecifico(String jsonString, int idLanguage) throws JSONException {
        this.jsonObject = new JSONObject(jsonString);
        this.idLanguage=idLanguage;
    }**/

    public ParserJSONOpcionesProductoEspecifico(JSONObject jsonObject, int idLanguage) {
        this.opcionesProductosJSON = jsonObject;
        this.idLanguage = idLanguage;
    }

    public OpcionesProductos getEmpleado() throws JSONException {
        OpcionesProductos opcionesProductosObject;
        //JSONObject opcionesProductosJSON=jsonObject.getJSONObject("product_option_value");
        if(!opcionesProductosJSON.getString("color").isEmpty()){
            //Es un color
            JSONArray nameJsonArray=opcionesProductosJSON.getJSONArray("name");
            String nombre="";
            for(int i=0; i<nameJsonArray.length();i++){
                JSONObject nombrePorIdioma=nameJsonArray.getJSONObject(i);
                if(nombrePorIdioma.getInt("id")==idLanguage){
                    nombre=nombrePorIdioma.getString("value");
                }
            }
            Color color=new Color(opcionesProductosJSON.getString("color"), nombre, opcionesProductosJSON.getInt("id"));
            opcionesProductosObject=color;
        }else{
            //Es una talla
            JSONArray nameJsonArray=opcionesProductosJSON.getJSONArray("name");
            String nombre="";
            for(int i=0; i<nameJsonArray.length();i++){
                JSONObject nombrePorIdioma=nameJsonArray.getJSONObject(i);
                if(nombrePorIdioma.getInt("id")==idLanguage){
                    nombre=nombrePorIdioma.getString("value");
                }
            }
            Talla talla=new Talla(nombre,opcionesProductosJSON.getInt("id"));
            opcionesProductosObject=talla;
        }
        return opcionesProductosObject;
    }

}
