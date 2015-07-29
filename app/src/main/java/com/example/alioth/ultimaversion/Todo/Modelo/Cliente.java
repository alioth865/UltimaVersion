package com.example.alioth.ultimaversion.Todo.Modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alioth on 26/06/2015.
 */
public class Cliente implements Serializable {
    private int idCliente;
    private String firstname;
    private String lastname;
    private String email;
    private List<Direccion> direcciones;
    private TotalDeVentas ventas;

    public Cliente(int idCliente, String firstname, String lastname, String email, List<Direccion> direcciones, TotalDeVentas ventas) {
        this.idCliente = idCliente;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.direcciones = direcciones;
        this.ventas = ventas;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public TotalDeVentas getVentas() {
        return ventas;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
