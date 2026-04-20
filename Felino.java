package com.mycompany.zoologico;

/**
 *
 * @author Jhon
 */
public abstract class Felino extends Mamifero{
    protected double tamaniogarras;
    protected int velocidad;
    
    //Constructor
    
    public Felino (String Habitat,double Altura, double Largo, double Peso,String Nombrecientifico, double Tamaniogarras, int Velocidad){
        super(Habitat, Altura, Largo, Peso,Nombrecientifico);
        this.tamaniogarras = Tamaniogarras;
        this.velocidad = Velocidad;
        }
    
    public double getTamaniogarras(){
    return tamaniogarras;
    }
    public int getVelocidad(){
    return velocidad;
    }
 
}
