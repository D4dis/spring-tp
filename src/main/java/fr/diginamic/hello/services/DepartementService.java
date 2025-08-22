package fr.diginamic.hello.services;

import fr.diginamic.hello.dao.DepartementDao;
import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.models.Ville;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class DepartementService {
    @Autowired
    private DepartementDao departementDao;

    public List<Departement> extractDepartements() {
        return departementDao.findAll();
    }

    public Departement extractDepartement(int id) {
        return departementDao.findById(id);
    }

    public Departement extractDepartement(String nom) {
        return departementDao.findByName(nom);
    }

    public List<Departement> insertDepartement(Departement departement) {
        // Contrôles métier
        if (departement.getNom() == null || departement.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du département est obligatoire");
        }

        departementDao.save(departement);
        return departementDao.findAll();
    }

    public List<Departement> modifierDepartement(int id, Departement departementModifie) {
        Departement existant = departementDao.findById(id);
        if (existant == null) {
            throw new IllegalArgumentException("Département introuvable avec l'ID : " + id);
        }

        // Contrôles métier
        if (departementModifie.getNom() == null || departementModifie.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du département est obligatoire");
        }

        existant.setNom(departementModifie.getNom());
        return departementDao.findAll();
    }

    public List<Departement> supprimerDepartement(int id) {
        Departement existant = departementDao.findById(id);
        if (existant == null) {
            throw new IllegalArgumentException("Département introuvable avec l'ID : " + id);
        }

        // Contrôle métier : vérifier s'il y a des villes dans ce département
        if (existant.getVilles() != null && !existant.getVilles().isEmpty()) {
            throw new IllegalArgumentException("Impossible de supprimer un département qui contient des villes");
        }

        departementDao.delete(existant);
        return departementDao.findAll();
    }

    public List<Ville> getTopNVilles(int departementId, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Le nombre de villes doit être positif");
        }
        return departementDao.findTopNVillesByDepartement(departementId, n);
    }

    public List<Ville> getVillesByPopulation(int departementId, int minPop, int maxPop) {
        if (minPop < 0 || maxPop < 0) {
            throw new IllegalArgumentException("Les populations min et max doivent être positives");
        }
        if (minPop > maxPop) {
            throw new IllegalArgumentException("La population minimum ne peut pas être supérieure à la population maximum");
        }
        return departementDao.findVillesByDepartementAndPopulation(departementId, minPop, maxPop);
    }
}
