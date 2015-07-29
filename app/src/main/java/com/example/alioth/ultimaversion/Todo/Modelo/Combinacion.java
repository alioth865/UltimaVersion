package com.example.alioth.ultimaversion.Todo.Modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alioth on 16/06/2015.
 */
public class Combinacion implements Serializable {
    private int id;
    private int cantidad;
    private List<Integer> idImagenes;
    private List<OpcionesProductos> opcionesProductosList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public List<Integer> getIdImagenes() {
        return idImagenes;
    }

    public void setIdImagenes(List<Integer> idImagenes) {
        this.idImagenes = idImagenes;
    }

    public List<OpcionesProductos> getOpcionesProductosList() {
        return opcionesProductosList;
    }

    public void setOpcionesProductosList(List<OpcionesProductos> opcionesProductosList) {
        this.opcionesProductosList = opcionesProductosList;
    }

    @Override
    public boolean equals(Object o) {
        return id==((Combinacion)o).getId();
    }
}
