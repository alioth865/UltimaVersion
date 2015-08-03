package com.example.alioth.ultimaversion.Todo.Modelo;

import java.io.Serializable;

/**
 * Created by AliothAntonio on 29/07/2015.
 */
public class Pedido implements Serializable {
    private Combinacion combinacion;
    private int cantidad;

    public Pedido(Combinacion combinacion, int cantidad) {
        this.combinacion = combinacion;
        this.cantidad = cantidad;
    }

    public Combinacion getCombinacion() {
        return combinacion;
    }

    public void setCombinacion(Combinacion combinacion) {
        this.combinacion = combinacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
