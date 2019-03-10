package com.bubble;

/**
 * Created by Daniel on 01/04/2016.
 */

/* Clase que implementa un objeto puntuación, que se usará en la
   clase ListaPuntuaciones para almacenar estas.
 */
public class Puntuacion implements Comparable<Puntuacion> {
    private String nombre;
    private int    tiempo;

    public Puntuacion(String nombre, int puntos) {
        this.nombre = nombre;
        this.tiempo = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int puntos) {
        this.tiempo = puntos;
    }

    @Override
    public int compareTo(Puntuacion o) {                //Se sobrecarga el operador compareTo para
        if (this.tiempo < o.getTiempo()) return 1;      //poder ordenar este tipo de objeto.
        else if(this.tiempo > o.getTiempo()) return -1;
        else return 0;
    }
}
