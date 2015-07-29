package com.example.alioth.ultimaversion.Todo.Parser;

import com.example.alioth.ultimaversion.Todo.Modelo.Direccion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alioth on 29/06/2015.
 */
public class ParserJSONDirecciones {
    JSONObject jsonObject;
    public ParserJSONDirecciones(JSONObject jsonObject) {
        this.jsonObject=jsonObject;
    }

    public List<Direccion> getDirecciones() throws JSONException {
        List<Direccion> direccions=new LinkedList<>();
        JSONArray array=jsonObject.getJSONArray("addresses");
        for (int i = 0; i < array.length(); i++) {
            int idDireccion=array.getJSONObject(i).getInt("id");
            String direccion1=array.getJSONObject(i).getString("address1");
            String direccion2=array.getJSONObject(i).getString("address2");
            String ciudad=array.getJSONObject(i).getString("city");
            String postcode=array.getJSONObject(i).getString("postcode");
            Direccion d=new Direccion(idDireccion,direccion1,direccion2,ciudad,postcode);
            direccions.add(d);
        }
        return direccions;
    }
}
