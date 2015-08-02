package com.example.alioth.ultimaversion.Todo.Modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alioth on 16/06/2015.
 */
public class Combinacion implements Serializable {
    private int id;
    private int cantidad;
    private int idProducto;


    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Combinacion that = (Combinacion) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public String toString() {
        String salida="";
        for(OpcionesProductos op:opcionesProductosList){
            if(op instanceof Talla)
                salida+="SIZE: "+((Talla)op).getTalla()+" ";
            if(op instanceof Color)
                salida+="COLOR: "+((Color)op).getNombreColor()+" ";
        }
        return salida;
    }
}
