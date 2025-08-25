//package fr.diginamic.hello.config;
//
//import fr.diginamic.hello.models.Departement;
//import fr.diginamic.hello.models.Ville;
//import fr.diginamic.hello.services.DepartementService;
//import fr.diginamic.hello.services.VilleService;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//
//@Component
//public class DataInitializer {
//
//    @Autowired
//    private DepartementService departementService;
//
//    @Autowired
//    private VilleService villeService;
//
//    @PostConstruct
//    public void init() {
//        System.out.println("=== Initialisation des données simplifiée ===");
//
//        // Création des départements
//        Departement paris = new Departement();
//        paris.setNom("Paris");
//        paris.setCode("75");
//        paris.setVilles(new ArrayList<>());
//        departementService.insertDepartement(paris);
//
//        Departement rhone = new Departement();
//        rhone.setNom("Rhône");
//        rhone.setCode("69");
//        rhone.setVilles(new ArrayList<>());
//        departementService.insertDepartement(rhone);
//
//        Departement bdr = new Departement();
//        bdr.setNom("Bouches-du-Rhône");
//        bdr.setCode("13");
//        bdr.setVilles(new ArrayList<>());
//        departementService.insertDepartement(bdr);
//
//        Departement hg = new Departement();
//        hg.setNom("Haute-Garonne");
//        hg.setCode("31");
//        hg.setVilles(new ArrayList<>());
//        departementService.insertDepartement(hg);
//
//        Departement herault = new Departement();
//        herault.setNom("Hérault");
//        herault.setCode("34");
//        herault.setVilles(new ArrayList<>());
//        departementService.insertDepartement(herault);
//
//        // Création des villes
//        creerVille("Paris", 2165423, paris);
//        creerVille("Lyon", 518635, rhone);
//        creerVille("Marseille", 873076, bdr);
//        creerVille("Toulouse", 479553, hg);
//        creerVille("Montpellier", 295542, herault);
//
//        System.out.println("✓ Données initialisées : "
//                + departementService.extractDepartements().size() + " départements, "
//                + villeService.extractVilles().size() + " villes");
//    }
//
//    private void creerVille(String nom, int nbHabitants, Departement departement) {
//        Ville ville = new Ville();
//        ville.setNom(nom);
//        ville.setNbHabitants(nbHabitants);
//        ville.setDepartement(departement);
//
//        villeService.insertVille(ville);
//
//        // Ajout au département
//        departement.getVilles().add(ville);
//    }
//}
