package com.mycompany.zoologico;

/**
 *
 * @author Jhon
 */
public abstract class Canino extends Mamifero {
    protected String color;
    protected double tamaniocolmillos;
    
    //Constructor
    public Canino(String Habitat, double Altura,double Largo, double Peso, String Nombrecientifico, String Color, double Tamaniocolmillos ){
    super(Habitat, Altura, Largo, Peso, Nombrecientifico);
    this.color = Color;
    this.tamaniocolmillos =Tamaniocolmillos;
            
    }
    
    public String getColor(){
    return color;
    }
    public double getTamaniocolmillos(){
    return tamaniocolmillos;
    }
}

