package fr.diginamic.hello.dto;

public class VilleDto {
    private int id;
    private String nom;
    private int nbHabitants;
    private String codeDepartement;
    private String nomDepartement;

    public VilleDto() {
    }

    public VilleDto(int id, String nom, int nbHabitants, String codeDepartement, String nomDepartement) {
        this.id = id;
        this.nom = nom;
        this.nbHabitants = nbHabitants;
        this.codeDepartement = codeDepartement;
        this.nomDepartement = nomDepartement;
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

    public String getCodeDepartement() {
        return codeDepartement;
    }

    public void setCodeDepartement(String codeDepartement) {
        this.codeDepartement = codeDepartement;
    }

    public String getNomDepartement() {
        return nomDepartement;
    }

    public void setNomDepartement(String nomDepartement) {
        this.nomDepartement = nomDepartement;
    }
}
