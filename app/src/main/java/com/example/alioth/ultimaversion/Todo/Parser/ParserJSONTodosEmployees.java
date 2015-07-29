package com.example.alioth.ultimaversion.Todo.Parser;

import com.example.alioth.ultimaversion.Todo.Modelo.Empleado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alioth on 15/06/2015.
 */
public class ParserJSONTodosEmployees {
    private JSONObject jsonObject;

    public ParserJSONTodosEmployees(String jsonString) throws JSONException {
        this.jsonObject = new JSONObject(jsonString);
    }

    public Map<Integer,Empleado> todosEmpleados() throws JSONException {
        Map<Integer,Empleado> todosEmpleados=new HashMap<>();
        JSONArray jsonArrayEmpleados=jsonObject.getJSONArray("employees");
        for(int i=0;i<jsonArrayEmpleados.length();i++){
            JSONObject tmp=jsonArrayEmpleados.getJSONObject(i);
            ParserJSONEmpleadoEspecifico empleadoEspecifico=new ParserJSONEmpleadoEspecifico(tmp);
            todosEmpleados.put(tmp.getInt("id"),empleadoEspecifico.getEmpleado());
        }
        return todosEmpleados;
    }
}
