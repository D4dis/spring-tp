package fr.diginamic.hello.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    private int code;

    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Ville> villes;

    public Departement() {
    }

    public Departement(int id, String nom, int code, List<Ville> villes) {
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Ville> getVilles() {
        return villes;
    }

    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }
}
