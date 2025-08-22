package fr.diginamic.hello.dao;

import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.models.Ville;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DepartementDao {

    private static List<Departement> departements = new ArrayList<>();
    private static int compteurId = 1;

    public List<Departement> findAll() {
        return new ArrayList<>(departements);
    }

    public Departement findById(int id) {
        return departements.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Departement findByName(String nom) {
        return departements.stream()
                .filter(d -> d.getNom().equalsIgnoreCase(nom))
                .findFirst()
                .orElse(null);
    }

    public Departement save(Departement departement) {
        // Vérifier si le département existe déjà
        if (findByName(departement.getNom()) != null) {
            throw new IllegalArgumentException("Un département avec ce nom existe déjà");
        }

        departement.setId(compteurId++);
        departements.add(departement);
        return departement;
    }

    public Departement update(Departement departement) {
        Departement existant = findById(departement.getId());
        if (existant != null) {
            existant.setNom(departement.getNom());
            // Note: on ne met pas à jour la liste des villes ici
        }
        return existant;
    }

    public void delete(Departement departement) {
        departements.removeIf(d -> d.getId() == departement.getId());
    }

    public boolean deleteById(int id) {
        return departements.removeIf(d -> d.getId() == id);
    }

    public List<Ville> findTopNVillesByDepartement(int departementId, int limit) {
        Departement dept = findById(departementId);
        if (dept == null || dept.getVilles() == null) {
            return new ArrayList<>();
        }

        return dept.getVilles().stream()
                .sorted((v1, v2) -> Integer.compare(v2.getNbHabitants(), v1.getNbHabitants()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Ville> findVillesByDepartementAndPopulation(int departementId, int minPop, int maxPop) {
        Departement dept = findById(departementId);
        if (dept == null || dept.getVilles() == null) {
            return new ArrayList<>();
        }

        return dept.getVilles().stream()
                .filter(v -> v.getNbHabitants() >= minPop && v.getNbHabitants() <= maxPop)
                .collect(Collectors.toList());
    }

    public int count() {
        return departements.size();
    }
}
