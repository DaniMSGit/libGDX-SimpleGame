package com.bubble;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Daniel on 01/04/2016.
 */

/*Clase que implementa una lista de 5 puntuaciones ordenada
  de mayor a menor, y los métodos de esta.
 */
public class ListaPuntuaciones {

    private ArrayList<Puntuacion> listap;

    public ListaPuntuaciones() {
        listap = new ArrayList<Puntuacion>();
    }

    public void actulizar(Puntuacion pt){
        if(listap.size()!=5){                                   //Si la lista no está llena
            listap.add(pt);                                     //Se inserta el elemento
            Collections.sort(listap);                           //Se ordena la lista
        }else{
            if(listap.get(listap.size()-1).compareTo(pt)==1){   //Si el elemento a insertar en mayor que el último
                listap.set(listap.size()-1, pt);                //Se reemplaza
            }
            Collections.sort(listap);                           //Se ordena
        }
    }

    public String getCadena(){
        String puntuaciones = "";
        for (int i = 0; i < listap.size(); i++) {
            puntuaciones = puntuaciones + listap.get(i).getNombre()+"  "+ listap.get(i).getTiempo()+"  ";
        }
        return puntuaciones;
    }

    public String mostrar(){
        String puntuaciones = "";
        for (int i = 0; i < listap.size(); i++) {
            puntuaciones = puntuaciones + listap.get(i).getNombre().toUpperCase()+"....."+ listap.get(i).getTiempo() +"s"+ "\n";
        }
        return puntuaciones;
    }

    public int getsize(){return listap.size();}
}
