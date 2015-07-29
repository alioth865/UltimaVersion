package com.example.alioth.ultimaversion.Todo.Modelo;

import java.io.Serializable;

/**
 * Created by Alioth on 29/06/2015.
 */
public class Direccion implements Serializable{
    private int idDireccion;
    private String direccion1;
    private String direccion2;
    private String ciudad;
    private String postcode;


    public Direccion(int idDireccion, String direccion1, String direccion2, String ciudad,  String postcode) {
        this.idDireccion = idDireccion;
        this.direccion1 = direccion1;
        this.direccion2 = direccion2;
        this.ciudad = ciudad;
        this.postcode= postcode;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public String getDireccion1() {
        return direccion1;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public String getCiudad() {
        return ciudad;
    }

    @Override
    public String toString() {
        return  direccion1 +" "+ direccion2 + " CP: " + postcode + ", " + ciudad;
    }
}
