package com.example.alioth.ultimaversion.Todo.Parser;

import com.example.alioth.ultimaversion.Todo.Modelo.Empleado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alioth on 15/06/2015.
 */
public class ParserJSONEmpleadoEspecifico {
    private JSONObject empleadoJson;

    /*public ParserJSONEmpleadoEspecifico(String jsonString) throws JSONException {
        this.jsonObject = new JSONObject(jsonString);
    }*/

    public ParserJSONEmpleadoEspecifico(JSONObject jsonObject) {
        this.empleadoJson = jsonObject;
    }

    public Empleado getEmpleado() throws JSONException {
        Empleado e=new Empleado();
        //JSONObject empleadoJson=jsonObject.getJSONObject("employee");
        e.setNombre(empleadoJson.getString("firstname"));
        e.setApellido(empleadoJson.getString("lastname"));
        e.setContraseña(empleadoJson.getString("passwd"));
        e.setEmail(empleadoJson.getString("email"));
        e.setId(empleadoJson.getInt("id"));
        return e;
    }
}
