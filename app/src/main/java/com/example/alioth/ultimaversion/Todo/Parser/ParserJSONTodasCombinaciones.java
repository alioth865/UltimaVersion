package com.example.alioth.ultimaversion.Todo.Parser;

import android.util.Log;

import com.example.alioth.ultimaversion.Todo.Modelo.Combinacion;
import com.example.alioth.ultimaversion.Todo.Modelo.OpcionesProductos;
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
public class ParserJSONTodasCombinaciones {
    private JSONObject todasCombinacionesJSON;
    Map<String, String> configuracion;
    Map<Integer, OpcionesProductos> todasOpciones;

    public ParserJSONTodasCombinaciones(String todasCombinacionesJSON, Map<String, String> configuracion, Map<Integer, OpcionesProductos> todasOpciones) throws JSONException {
        this.todasCombinacionesJSON = new JSONObject(todasCombinacionesJSON);
        this.configuracion=configuracion;
        this.todasOpciones=todasOpciones;
    }

    public Map<Integer, Combinacion> todasLasCombinaciones() throws JSONException, IOException {
        JSONArray jsonArray=todasCombinacionesJSON.getJSONArray("combinations");
        Map<Integer, Combinacion> map=new HashMap<>();
        for(int i=0;i<jsonArray.length();i++){
            int id=jsonArray.getJSONObject(i).getInt("id");
            ParserJSONCombinationEspecifica parserJSONCombinationEspecifica=new ParserJSONCombinationEspecifica(jsonArray.getJSONObject(i),todasOpciones);
            map.put(id, parserJSONCombinationEspecifica.combinacionEspecifica());
        }
        String urlStockAvailables="http://"+configuracion.get("shop_url")+"/"+configuracion.get("name_shop")+"/api/stock_availables/?display=full&output_format=JSON";
        InputStream inputStream=Utilidades.peticionHttp(configuracion.get("key"),"",urlStockAvailables);
        String jsonObject=Utilidades.getStringFromInputStream(inputStream);
        ParserJSONStockAvailables parserJSONStockAvailables=new ParserJSONStockAvailables(jsonObject,map);
        return map;
    }
}
