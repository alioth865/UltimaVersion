package com.example.alioth.ultimaversion.Todo.Modelo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alioth on 29/06/2015.
 */
public class TotalDeVentas implements Serializable {
    List<Double> totailPaid;

    public TotalDeVentas() {
        totailPaid=new LinkedList<>();
    }

    public void addPago(double d){
        totailPaid.add(d);
    }

    public double totalVentas(){
        double cont=0;
        for(Double d:totailPaid){
            cont+=d;
        }
        return cont;
    }
}
