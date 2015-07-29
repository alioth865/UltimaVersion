package com.example.alioth.ultimaversion.Todo.Modelo;

import java.io.Serializable;

/**
 * Created by Alioth on 16/06/2015.
 */
public class Talla extends OpcionesProductos implements Serializable {
    private String talla;


    public Talla(String talla, int id) {
        super(id);
        this.talla = talla;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }
}
