package fr.diginamic.hello.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class Ville {
    @Positive(message = "L'id doit etre strictement positif")
    private int id;
    @NotNull(message = "Le nom de la ville ne peut pas etre null")
    @Size(min = 2, message = "Le nom doit contenir au moins 2 caracteres")
    private String nom;
    @Min(value = 1, message = "Le nombre d'habitants doit etre superieur ou egal a 1")
    private int nbHabitants;

    public Ville() {
    }

    public Ville(int id, String nom, int nbHabitants) {
        this.id = id;
        this.nom = nom;
        this.nbHabitants = nbHabitants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbHabitants() {
        return nbHabitants;
    }

    public void setNbHabitants(int nbHabitants) {
        this.nbHabitants = nbHabitants;
    }
}
