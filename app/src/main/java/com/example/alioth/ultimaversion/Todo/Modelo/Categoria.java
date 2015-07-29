package com.example.alioth.ultimaversion.Todo.Modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alioth on 16/06/2015.
 */
public class Categoria implements Serializable{
    private int id;
    private String name;
    private List<Producto> productos;

    public Categoria(int id, String name, List<Producto> productos) {
        this.id = id;
        this.name = name;
        this.productos = productos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
