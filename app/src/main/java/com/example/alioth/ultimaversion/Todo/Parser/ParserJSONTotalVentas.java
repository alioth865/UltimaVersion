package com.example.alioth.ultimaversion.Todo.Parser;

import com.example.alioth.ultimaversion.Todo.Modelo.TotalDeVentas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alioth on 29/06/2015.
 */
public class ParserJSONTotalVentas {
    private JSONObject jsonObject;
    public ParserJSONTotalVentas(JSONObject jsonObject) {
        this.jsonObject=jsonObject;
    }

    public TotalDeVentas getTotalVentas() throws JSONException {
        TotalDeVentas tv=new TotalDeVentas();
        JSONArray array=jsonObject.getJSONArray("orders");
        for (int i = 0; i < array.length(); i++) {
            tv.addPago(array.getJSONObject(i).getDouble("total_paid"));
        }
        return tv;
    }
}
