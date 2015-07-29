package com.example.alioth.ultimaversion.Todo.Modelo;

import java.io.Serializable;

/**
 * Created by Alioth on 16/06/2015.
 */
public class OpcionesProductos implements Serializable {
    private int id;

    public OpcionesProductos(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        return this.id==((OpcionesProductos)o).getId();
    }
}
