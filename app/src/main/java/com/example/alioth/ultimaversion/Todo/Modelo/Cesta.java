package com.example.alioth.ultimaversion.Todo.Modelo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by AliothAntonio on 29/07/2015.
 */
public class Cesta implements Serializable {
    private List<Pedido> cesta;
    private Map<Integer, Producto> todosProductos;

    public Cesta(Map<Integer, Producto> todosProductos) {
        this.cesta = new LinkedList<>();
        this.todosProductos=todosProductos;
    }

    public void addProducto(Combinacion c, Integer cantidad){
            boolean isAñadido=false;
            for(Pedido p:cesta){
                if(p.getCombinacion().equals(c)){
                    p.setCantidad(p.getCantidad()+cantidad);
                    isAñadido=true;
                    break;
                }
            }
            if(!isAñadido){
                cesta.add(new Pedido(c,cantidad));
            }
    }

    public double calcularPrecioTotal(){
        double cantidad=0f;
        for(Pedido p:cesta){
            cantidad+=todosProductos.get(p.getCombinacion().getIdProducto()).getPrecio();
        }
        return cantidad;
    }

    public double precioEspecifico(int id){
        return todosProductos.get(id).getPrecio();
    }

    public void eliminar(int pos){
        cesta.remove(pos);
    }

    public List<Pedido> getCesta() {
        return cesta;
    }
}
