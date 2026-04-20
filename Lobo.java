
package com.mycompany.zoologico;


public class Lobo extends Canino{
    private int numerocamada;
    private String especielobo;
    
    //Constructor

    public Lobo(int numerocamada, String especielobo, String Habitat, double Altura, double Largo, double Peso, String Nombrecientifico, String Color, double Tamaniocolmillos) {
        super(Habitat, Altura, Largo, Peso, Nombrecientifico, Color, Tamaniocolmillos);
        this.numerocamada = numerocamada;
        this.especielobo = especielobo;
    }
    
    @Override
    public String comer(){
    return "El lobo caza en grupo con " +numerocamada+ "y se alimenta";
    }
    @Override
    public String correr(){
    return "El lobo corre a rapidamente por ser parte de una especie"+especielobo;}
    @Override
    public String dormir(){
    return "El lobo " + color+ " duerme en las cavernas de "+ habitat;
    }
    @Override
    public String comunicarse(){
    return "El lobo aulla ";
    }
    
}
