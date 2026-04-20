

package com.mycompany.zoologico;


public class Zoologico {

    public static void main(String[] args) {
        Leon leon = new Leon(
                "Sabana", 1.2, 2.0, 180,
                "Panthera leo", 5.0, 50, 8, 110
        );

        Tigre tigre = new Tigre(
                "Bengala", "Selva", 1.1, 2.2, 200,
                "Panthera tigris", 6.0, 45
        );

        Guepardo guepardo = new Guepardo(
                "Sabana", 0.9, 1.4, 70,
                "Acinonyx jubatus", 4.0, 100
        );

        Lobo lobo = new Lobo(
                5, "Ártico", "Tundra", 0.8, 1.5,
                45, "Canis lupus", "Blanco", 4.0
        );

        Perro perro = new Perro(
                400, "Sabana", 0.7, 1.1,
                25, "Lycaon pictus", "Manchado", 3.0
        );

        // 🔹 Arreglo (polimorfismo)
        Mamifero[] animales = new Mamifero[5];

        animales[0] = leon;
        animales[1] = tigre;
        animales[2] = guepardo;
        animales[3] = lobo;
        animales[4] = perro;

        
        for (Mamifero animal : animales) {
            System.out.println("----------------------");
            System.out.println("Nombre: " + animal.getNombrecientifico());
            System.out.println("Habitat: " + animal.getHabitat());

            System.out.println(animal.comer());
            System.out.println(animal.dormir());
            System.out.println(animal.correr());
            System.out.println(animal.comunicarse());
        }

        // ⭐ EVIDENCIA DE SOBRECARGA 
        System.out.println("\n--- Sobrecarga de método en Perro ---");
        System.out.println(perro.comer());           // método normal
        System.out.println(perro.comer("carne"));    // método sobrecargado
    }
}
