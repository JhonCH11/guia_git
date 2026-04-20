
package com.mycompany.zoologico;

public class Guepardo extends Felino{

    public Guepardo(String Habitat, double Altura, double Largo, double Peso, String Nombrecientifico, double Tamaniogarras, int Velocidad) {
        super(Habitat, Altura, Largo, Peso, Nombrecientifico, Tamaniogarras, Velocidad);
    }
    
    @Override
    public String dormir(){
        return "El guepardo duerme durante el dia y se mantiene despierto por la noche";
    }       
    @Override
    public String correr(){
        return "El guepardo corre a una velocidad de "+velocidad + "km/h";
    }
    public String comer(){
        return "El guepardo puede cazar a sus presas por sus garras que miden "+tamaniogarras + "centimetros";
    }
    
    public String comunicarse(){
        return "El guepardo "+nombrecientifico+ "hace un sonido agudo y rápido" ;
    }
}
