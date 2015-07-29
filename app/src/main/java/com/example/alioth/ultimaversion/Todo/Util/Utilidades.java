package com.example.alioth.ultimaversion.Todo.Util;

import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.util.Log;

import com.example.alioth.ultimaversion.R;
import com.example.alioth.ultimaversion.Todo.Modelo.Color;
import com.example.alioth.ultimaversion.Todo.Modelo.Combinacion;
import com.example.alioth.ultimaversion.Todo.Modelo.OpcionesProductos;

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alioth on 15/06/2015.
 */
public class Utilidades {
    public static final int idFragmentCategoria=1;
    public static final int idFragmentProducto=2;
    public static final int idFragmentProductoEspecifico=3;
    public static final int idFragmentClientes=4;
    public static final int idFragmentClienteEspecifico=5;
    public static final int idFragmentCrearPedido=6;
    public static final int idFragmentBuscarProducto=7;
    public static final int idFragmentElegirCombinacion=8;

    public static InputStream peticionHttp(String usuario, String contraseña, String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection uc = (HttpURLConnection )url.openConnection();
        String userpass = usuario + ":" + contraseña;
        String basicAuth = "Basic " + new String(Base64.encodeBase64(userpass.getBytes()));
        uc.setRequestProperty ("Authorization", basicAuth);
        InputStream inputStream=uc.getInputStream();
        //uc.disconnect();
        return inputStream;
    }

    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }


    public static Map<String, String> Config(Resources r){
        Map<String,String> m=new HashMap<>();
        try
        {
            InputStreamReader isr = new InputStreamReader(r.openRawResource(R.raw.info));
            BufferedReader br = new BufferedReader(isr);
            String linea;
            while ((linea=br.readLine())!=null){
                String[] parts=linea.split(":");
                m.put(parts[0],parts[1]);
            }
            br.close();
            isr.close();
        }
        catch (IOException ex){
            Log.e("Ficheros", "Error al leer fichero desde recurso raw");
            return null;
        }
        return m;
    }

    public static List<OpcionesProductos> buscarOpciones(List<Combinacion> combinacions, OpcionesProductos opcionesProductos){
        //Esto se va a usar cada vez que se cambie el spiner con las opciones del producto
        List<OpcionesProductos> retorno=new LinkedList<>();
        for(Combinacion tmp: combinacions){
            List<OpcionesProductos> opciones=tmp.getOpcionesProductosList();
            int pos=opciones.indexOf(opcionesProductos);
            if(opciones.contains(opcionesProductos)){
                for(OpcionesProductos tmp1:opciones){
                    if(!tmp1.equals(opcionesProductos))
                        retorno.add(tmp1);
                }
            }
        }
        return retorno;
    }

    public static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    public static Set<Color> colores(List<Combinacion> combinacions){
        Set<Color> colores=new HashSet<>();
        for(Combinacion c:combinacions){
            for(OpcionesProductos op:c.getOpcionesProductosList()){
                if(op instanceof Color){
                    colores.add(((Color) op));
                }
            }
        }
        return colores;
    }

    public static Set<Combinacion> tallasPorColor(List<Combinacion> combinacions, int color){
        Set<Combinacion> combinacionSet=new HashSet<>();
        for(Combinacion c:combinacions){
            for(OpcionesProductos op:c.getOpcionesProductosList()){
                if(op instanceof Color && ((Color) op).getColor()==color){
                    combinacionSet.add(c);
                }
            }
        }
        return combinacionSet;
    }



}
