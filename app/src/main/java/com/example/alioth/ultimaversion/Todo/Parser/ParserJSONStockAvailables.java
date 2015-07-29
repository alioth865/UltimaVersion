package com.example.alioth.ultimaversion.Todo.Parser;

import com.example.alioth.ultimaversion.Todo.Modelo.Combinacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Alioth on 30/06/2015.
 */
public class ParserJSONStockAvailables {
    private JSONObject jsonObject;
    private Map<Integer,Combinacion> combinacionMap;


    public ParserJSONStockAvailables(String jsonObject, Map<Integer,Combinacion> combinacionMap) throws JSONException {
        this.jsonObject = new JSONObject(jsonObject);
        this.combinacionMap=combinacionMap;
        asignarCantidad();
    }

    private void asignarCantidad() throws JSONException {
        JSONArray array=    jsonObject.getJSONArray("stock_availables");
        for (int i = 0; i < array.length(); i++) {
            int idCombinacion=array.getJSONObject(i).getInt("id_product_attribute");
            if(idCombinacion!=0){
                combinacionMap.get(idCombinacion).setCantidad(array.getJSONObject(i).getInt("quantity"));
            }
        }
    }
}
