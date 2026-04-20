
package com.mycompany.zoologico;

public class Perro extends Canino{
    private int fuerzamordida;

    public Perro(int fuerzamordida, String Habitat, double Altura, double Largo, double Peso, String Nombrecientifico, String Color, double Tamaniocolmillos) {
        super(Habitat, Altura, Largo, Peso, Nombrecientifico, Color, Tamaniocolmillos);
        this.fuerzamordida = fuerzamordida;
    }
    
    @Override
    public String comer() {
        return "El perro caza utilizando su fuerza de mordida de " + fuerzamordida + " psi";
    }

    @Override
    public String correr() {
        return "El perro corre rápidamente por el " + habitat;
    }

    @Override
    public String dormir() {
        return "El perro duerme en su refugio en " + habitat;
    }

    @Override
    public String comunicarse() {
        return "El perro ladra para comunicarse";
    }
    
    public String comer(String alimento) {
    return "El perro come " + alimento;
}
}
