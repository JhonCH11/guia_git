package com.mycompany.zoologico;

/**
 *
 * @author Jhon
 */
public abstract class Mamifero {
     protected String habitat;
     protected double altura;
     protected double largo;
     protected double peso;
     protected String nombrecientifico;
     
     //Consttructor
     
     public Mamifero(String Habitat,double Altura, double Largo, double Peso, String Nombrecientifico){
     this.habitat = Habitat;
     this.altura = Altura;
     this.largo = Largo;
     this.peso = Peso;
     this.nombrecientifico = Nombrecientifico;
     }
     
     public String getHabitat(){
     return habitat;
     }

    public double getAltura() {
        return altura;
    }

    public double getLargo() {
        return largo;
    }

    public double getPeso() {
        return peso;
    }

    public String getNombrecientifico() {
        return nombrecientifico;
    }
     
    //metodos abstractos
    
    public abstract String comer();
    public abstract String dormir();
    public abstract String correr ();
    public abstract String comunicarse();
    
     
}
