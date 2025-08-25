package fr.diginamic.hello.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departement")
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @NotNull(message = "Le nom du département ne peut pas être null")
    @Size(min = 2, message = "Le nom doit contenir au moins 2 caractères")
    @Column(nullable = true)
    private String nom;

    @Column(nullable = true, unique = true)
    private String code;

    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Ville> villes;

    public Departement() {
    }

    public Departement(int id, String nom, String code, List<Ville> villes) {
        this.id = id;
        this.nom = nom;
        this.code = code;
        this.villes = villes;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Ville> getVilles() {
        return villes;
    }

    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }

    public void addVille(Ville ville) {
        if (villes == null) {
            villes = new ArrayList<>();
        }
        villes.add(ville);
        ville.setDepartement(this);
    }

    public void removeVille(Ville ville) {
        if (villes != null) {
            villes.remove(ville);
            ville.setDepartement(null);
        }
    }
}
