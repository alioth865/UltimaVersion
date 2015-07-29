package com.example.alioth.ultimaversion.Todo.Modelo;

import java.io.Serializable;

/**
 * Created by Alioth on 15/06/2015.
 */
public class Empleado implements Serializable {
    private int id;
    private String nombre;
    private String apellido;
    private String contraseña;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
