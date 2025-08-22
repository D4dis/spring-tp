package fr.diginamic.hello.services;

import fr.diginamic.hello.dao.VilleDao;
import fr.diginamic.hello.models.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {
    @Autowired
    private VilleDao villeDao;

    public List<Ville> extractVilles() {
        return villeDao.findAll();
    }

    public Ville extractVille(int id) {
        return villeDao.findById(id);
    }

    public Ville extractVille(String str) {
        return villeDao.findByName(str);
    }

    public List<Ville> insertVille(Ville ville) {
        if (ville.getDepartement() == null) {
            throw new IllegalArgumentException("Une ville doit avoir un département");
        }
        if (ville.getNom() == null || ville.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la ville est obligatoire");
        }
        if (ville.getNbHabitants() <= 0) {
            throw new IllegalArgumentException("Le nombre d'habitants doit être positif");
        }
        villeDao.save(ville);
        return villeDao.findAll();
    }

    public List<Ville> modifierVille(int id, Ville villeModifiee) {
        Ville villeExistante = villeDao.findById(id);
        if (villeExistante == null) {
            throw new IllegalArgumentException("Ville introuvable avec l'ID : " + id);
        }
        if (villeModifiee.getNom() == null || villeModifiee.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la ville est obligatoire");
        }
        if (villeModifiee.getNbHabitants() <= 0) {
            throw new IllegalArgumentException("Le nombre d'habitants doit être positif");
        }
        villeExistante.setNom(villeModifiee.getNom());
        villeExistante.setNbHabitants(villeModifiee.getNbHabitants());

        return villeDao.findAll();
    }

    public List<Ville> supprimerVille(int id) {
        Ville villeExistante = villeDao.findById(id);
        if (villeExistante == null) {
            throw new IllegalArgumentException("Ville introuvable avec l'ID : " + id);
        }
        villeDao.delete(villeExistante);
        return villeDao.findAll();
    }
}
