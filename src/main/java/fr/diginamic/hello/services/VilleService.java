package fr.diginamic.hello.services;

import fr.diginamic.hello.models.Ville;
import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.repositories.VilleRepository;
import fr.diginamic.hello.repositories.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VilleService {

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private DepartementRepository departementRepository;

    @Transactional(readOnly = true)
    public Page<Ville> extractVillesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return villeRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Ville> extractVilles() {
        return villeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Ville extractVille(int id) {
        return villeRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Ville extractVille(String nom) {
        return villeRepository.findByNomIgnoreCase(nom).orElse(null);
    }

    public Ville insertVille(Ville ville) {
        validateVille(ville);

        if (ville.getDepartement() != null && ville.getDepartement().getId() != 0) {
            Optional<Departement> dept = departementRepository.findById(ville.getDepartement().getId());
            if (dept.isEmpty()) {
                throw new IllegalArgumentException("Le departement specifie n'existe pas");
            }
            ville.setDepartement(dept.get());
        }

        if (villeRepository.findByNomIgnoreCase(ville.getNom()).isPresent()) {
            throw new IllegalArgumentException("Une ville avec ce nom existe dejà");
        }

        return villeRepository.save(ville);
    }

    public Ville modifierVille(int id, Ville villeModifiee) {
        Ville villeExistante = villeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ville introuvable avec l'ID : " + id));

        validateVille(villeModifiee);

        Optional<Ville> villeAvecMemeNom = villeRepository.findByNomIgnoreCase(villeModifiee.getNom());
        if (villeAvecMemeNom.isPresent() && villeAvecMemeNom.get().getId() != id) {
            throw new IllegalArgumentException("Une autre ville avec ce nom existe dejà");
        }

        villeExistante.setNom(villeModifiee.getNom());
        villeExistante.setNbHabitants(villeModifiee.getNbHabitants());

        if (villeModifiee.getDepartement() != null && villeModifiee.getDepartement().getId() != 0) {
            Optional<Departement> dept = departementRepository.findById(villeModifiee.getDepartement().getId());
            if (dept.isEmpty()) {
                throw new IllegalArgumentException("Le departement specifie n'existe pas");
            }
            villeExistante.setDepartement(dept.get());
        }

        return villeRepository.save(villeExistante);
    }

    public void supprimerVille(int id) {
        Ville villeExistante = villeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ville introuvable avec l'ID : " + id));

        villeRepository.delete(villeExistante);
    }

    // Methodes pour les recherches avancees
    @Transactional(readOnly = true)
    public List<Ville> findVillesStartingWith(String prefix) {
        if (prefix == null || prefix.trim().isEmpty()) {
            throw new IllegalArgumentException("Le prefixe ne peut pas être vide");
        }
        return villeRepository.findByNomStartingWithIgnoreCaseOrderByNomAsc(prefix);
    }

    @Transactional(readOnly = true)
    public List<Ville> findVillesWithPopulationGreaterThan(int min) {
        if (min < 0) {
            throw new IllegalArgumentException("La population minimum doit être positive");
        }
        return villeRepository.findByNbHabitantsGreaterThanOrderByNbHabitantsDesc(min);
    }

    @Transactional(readOnly = true)
    public List<Ville> findVillesWithPopulationBetween(int min, int max) {
        validatePopulationRange(min, max);
        return villeRepository.findByNbHabitantsBetweenOrderByNbHabitantsDesc(min, max);
    }

    @Transactional(readOnly = true)
    public List<Ville> findVillesByDepartementWithPopulationGreaterThan(int departementId, int min) {
        validateDepartementExists(departementId);
        if (min < 0) {
            throw new IllegalArgumentException("La population minimum doit être positive");
        }
        return villeRepository.findByDepartementIdAndNbHabitantsGreaterThan(departementId, min);
    }

    @Transactional(readOnly = true)
    public List<Ville> findVillesByDepartementWithPopulationBetween(int departementId, int min, int max) {
        validateDepartementExists(departementId);
        validatePopulationRange(min, max);
        return villeRepository.findByDepartementIdAndNbHabitantsBetween(departementId, min, max);
    }

    @Transactional(readOnly = true)
    public List<Ville> findTopNVillesByDepartement(int departementId, int n) {
        validateDepartementExists(departementId);
        if (n <= 0) {
            throw new IllegalArgumentException("Le nombre de villes doit être positif");
        }
        return villeRepository.findTopNVillesByDepartementId(departementId, n);
    }

    // Methodes utilitaires privees
    private void validateVille(Ville ville) {
        if (ville.getDepartement() == null) {
            throw new IllegalArgumentException("Une ville doit avoir un departement");
        }
        if (ville.getNom() == null || ville.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la ville est obligatoire");
        }
        if (ville.getNbHabitants() <= 0) {
            throw new IllegalArgumentException("Le nombre d'habitants doit être positif");
        }
    }

    private void validatePopulationRange(int min, int max) {
        if (min < 0 || max < 0) {
            throw new IllegalArgumentException("Les populations min et max doivent être positives");
        }
        if (min > max) {
            throw new IllegalArgumentException("La population minimum ne peut pas être superieure à la population maximum");
        }
    }

    private void validateDepartementExists(int departementId) {
        if (!departementRepository.existsById(departementId)) {
            throw new IllegalArgumentException("Le departement avec l'ID " + departementId + " n'existe pas");
        }
    }
}