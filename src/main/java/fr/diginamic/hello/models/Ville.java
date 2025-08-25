package fr.diginamic.hello.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ville")
public class Ville {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Positive(message = "L'id doit etre strictement positif")
    private int id;

    @NotNull(message = "Le nom de la ville ne peut pas etre null")
    @Size(min = 2, message = "Le nom doit contenir au moins 2 caracteres")
    @Column(nullable = false)
    private String nom;

    @Min(value = 1, message = "Le nombre d'habitants doit etre superieur ou egal a 1")
    @Column(name = "nb_habs", nullable = false)
    private int nbHabitants;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_dept", nullable = false)
    @JsonBackReference
    private Departement departement;

    public Ville() {
    }

    public Ville(int id, String nom, int nbHabitants, Departement departement) {
        this.id = id;
        this.nom = nom;
        this.nbHabitants = nbHabitants;
        this.departement = departement;
    }

//    @Positive(message = "L'id doit etre strictement positif")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull(message = "Le nom de la ville ne peut pas etre null") @Size(min = 2, message = "Le nom doit contenir au moins 2 caracteres") String getNom() {
        return nom;
    }

    public void setNom(@NotNull(message = "Le nom de la ville ne peut pas etre null") @Size(min = 2, message = "Le nom doit contenir au moins 2 caracteres") String nom) {
        this.nom = nom;
    }

    @Min(value = 1, message = "Le nombre d'habitants doit etre superieur ou egal a 1")
    public int getNbHabitants() {
        return nbHabitants;
    }

    public void setNbHabitants(@Min(value = 1, message = "Le nombre d'habitants doit etre superieur ou egal a 1") int nbHabitants) {
        this.nbHabitants = nbHabitants;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
}
