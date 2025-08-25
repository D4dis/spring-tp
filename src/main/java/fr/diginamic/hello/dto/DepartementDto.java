package fr.diginamic.hello.dto;

import java.util.List;

public class DepartementDto {
    private int id;
    private String code;
    private String nom;
    private int nbHabitants;
    private List<VilleDto> villes;

    public DepartementDto() {
    }

    public DepartementDto(int id, String  code, String nom, int nbHabitants, List<VilleDto> villes) {
        this.id = id;
        this.code = code;
        this.nom = nom;
        this.nbHabitants = nbHabitants;
        this.villes = villes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String  getCode() {
        return code;
    }

    public void setCode(String  code) {
        this.code = code;
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

    public List<VilleDto> getVilles() {
        return villes;
    }

    public void setVilles(List<VilleDto> villes) {
        this.villes = villes;
    }
}
