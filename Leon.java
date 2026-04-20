
package com.mycompany.zoologico;

public class Leon extends Felino {
    private int numeromanada;
    private double potenciarugido;
    
    public Leon(String Habitat, double Altura,double Largo, double Peso,String Nombrecientifico,double Tamaniogarras
        ,int Velocidad,int Numeromanada,double Potenciarugido){
        super(Habitat,Altura,Largo,Peso,Nombrecientifico,Tamaniogarras,Velocidad);
        this.numeromanada = Numeromanada;
        this.potenciarugido= Potenciarugido;
    }
    @Override
    public String comer(){
        return "El leon caza en manada de: " +numeromanada;
    }
    
    @Override
    public String dormir(){
        return "El leon duerme  en la selva de: "+habitat;
    }
    @Override
    public String correr(){
        return "El leon corre a " +velocidad + "km/h";
    }
    @Override
    public String comunicarse(){
        return "El leon ruge con potencia de "+ potenciarugido;
    }
  
}
