package com.example.alioth.ultimaversion.Todo.Modelo;

import java.io.Serializable;

/**
 * Created by Alioth on 16/06/2015.
 */
public class Color extends OpcionesProductos implements Serializable {
    private String color;
    private String nombreColor;



    public Color(String color, String nombreColor, int id) {
        super(id);
        this.color = color;
        this.nombreColor=nombreColor;
    }

    public int getColor() {
        return android.graphics.Color.parseColor(color);
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNombreColor() {
        return nombreColor;
    }

    public void setNombreColor(String nombreColor) {
        this.nombreColor = nombreColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Color color1 = (Color) o;

        if (color != null ? !color.equals(color1.color) : color1.color != null) return false;

        return true;
    }


}
