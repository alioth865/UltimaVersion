package com.example.alioth.ultimaversion.Todo.Modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alioth on 16/06/2015.
 */
public class Producto implements Serializable {
    private int id;
    private int idDefaultImage;
    private double precio;
    private String referencia;
    private String descripcion;
    private String nombre;
    private List<Combinacion> combinacions;
    private List<Integer> idImagenes;

    public Producto(int id, int idDefaultImage, double precio, String referencia, String descripcion, String nombre, List<Combinacion> combinacions, List<Integer> idImagenes) {
        this.id = id;
        this.idDefaultImage = idDefaultImage;
        this.precio = precio;
        this.referencia = referencia;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.combinacions = combinacions;
        this.idImagenes=idImagenes;
    }

    @Override
    public boolean equals(Object o) {
        return id==((Producto)o).getId()
                ;
    }

    public List<Integer> getIdImagenes() {
        return idImagenes;
    }

    public void setIdImagenes(List<Integer> idImagenes) {
        this.idImagenes = idImagenes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDefaultImage() {
        return idDefaultImage;
    }

    public void setIdDefaultImage(int idDefaultImage) {
        this.idDefaultImage = idDefaultImage;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Combinacion> getCombinacions() {
        return combinacions;
    }

    public void setCombinacions(List<Combinacion> combinacions) {
        this.combinacions = combinacions;
    }
}
