
package com.mycompany.zoologico;

public class Tigre extends Felino{
    private String especietigre;

    public Tigre(String Especietigre, String Habitat, double Altura, double Largo, double Peso, String Nombrecientifico, double Tamaniogarras, int Velocidad) {
        super(Habitat, Altura, Largo, Peso, Nombrecientifico, Tamaniogarras, Velocidad);
        this.especietigre = Especietigre;
    }
    @Override
    public String comer(){
        return "El tigre come carne"; }
    @Override
    public String dormir(){
        return "El tigre duerme en "+ habitat+ " cuando necesita descansar";
    }
    @Override
    public String correr(){
        return "La especie de tigre "+especietigre+ " puede correr a " +velocidad +"km/h";
    }
    @Override
    public String comunicarse(){
        return "El tigre ruge";
    }  
}
