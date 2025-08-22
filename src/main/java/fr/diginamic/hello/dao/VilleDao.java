package fr.diginamic.hello.dao;

import fr.diginamic.hello.models.Ville;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VilleDao {

    private static List<Ville> villes = new ArrayList<>();
    private static int compteurId = 1;

    public List<Ville> findAll() {
        return new ArrayList<>(villes);
    }

    public Ville findById(int id) {
        return villes.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Ville findByName(String str) {
        return villes.stream()
                .filter(v -> v.getNom().equalsIgnoreCase(str))
                .findFirst()
                .orElse(null);
    }

    public Ville save(Ville ville) {
        if (findByName(ville.getNom()) != null) {
            throw new IllegalArgumentException("Une ville avec ce nom existe deja");
        }
        ville.setId(compteurId++);
        villes.add(ville);
        return ville;
    }

    public Ville update(Ville ville) {
        Ville existante = findById(ville.getId());
        if (existante != null) {
            existante.setNom(ville.getNom());
            existante.setNbHabitants(ville.getNbHabitants());
            existante.setDepartement(ville.getDepartement());
        }
        return existante;
    }

    public void delete(Ville ville) {
        villes.removeIf(v -> v.getId() == ville.getId());
    }

    public boolean deleteById(int id) {
        return villes.removeIf(v -> v.getId() == id);
    }

    public int count() {
        return villes.size();
    }
}
