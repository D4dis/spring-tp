package fr.diginamic.hello.services;

import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.models.Ville;
import fr.diginamic.hello.repositories.DepartementRepository;
import fr.diginamic.hello.repositories.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private VilleRepository villeRepository;

    @Transactional(readOnly = true)
    public List<Departement> extractDepartements() {
        return departementRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Departement> extractDepartementsWithVilles() {
        return departementRepository.findAllWithVilles();
    }

    @Transactional(readOnly = true)
    public Departement extractDepartement(int id) {
        return departementRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Departement extractDepartementWithVilles(int id) {
        return departementRepository.findByIdWithVilles(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Departement extractDepartement(String nom) {
        return departementRepository.findByNomIgnoreCase(nom).orElse(null);
    }

    public Departement insertDepartement(Departement departement) {
        validateDepartement(departement);

        if (departementRepository.existsByNomIgnoreCase(departement.getNom())) {
            throw new IllegalArgumentException("Un departement avec ce nom existe dejà");
        }

        if (departement.getCode() != null && departementRepository.existsByCode(departement.getCode())) {
            throw new IllegalArgumentException("Un departement avec ce code existe dejà");
        }

        return departementRepository.save(departement);
    }

    public Departement modifierDepartement(int id, Departement departementModifie) {
        Departement existant = departementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Departement introuvable avec l'ID : " + id));

        validateDepartement(departementModifie);

        Optional<Departement> deptAvecMemeNom = departementRepository.findByNomIgnoreCase(departementModifie.getNom());
        if (deptAvecMemeNom.isPresent() && deptAvecMemeNom.get().getId() != id) {
            throw new IllegalArgumentException("Un autre departement avec ce nom existe dejà");
        }

        if (departementModifie.getCode() != null && departementModifie.getCode() != existant.getCode()) {
            Optional<Departement> deptAvecMemeCode = departementRepository.findByCode(departementModifie.getCode());
            if (deptAvecMemeCode.isPresent() && deptAvecMemeCode.get().getId() != id) {
                throw new IllegalArgumentException("Un autre departement avec ce code existe dejà");
            }
        }

        existant.setNom(departementModifie.getNom());
        if (departementModifie.getCode() != null) {
            existant.setCode(departementModifie.getCode());
        }

        return departementRepository.save(existant);
    }

    public void supprimerDepartement(int id) {
        Departement existant = departementRepository.findByIdWithVilles(id)
                .orElseThrow(() -> new IllegalArgumentException("Departement introuvable avec l'ID : " + id));

        if (existant.getVilles() != null && !existant.getVilles().isEmpty()) {
            throw new IllegalArgumentException("Impossible de supprimer un departement qui contient des villes");
        }

        departementRepository.delete(existant);
    }

    // Methodes deleguees au VilleRepository pour maintenir la compatibilite
    @Transactional(readOnly = true)
    public List<Ville> getTopNVilles(int departementId, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Le nombre de villes doit être positif");
        }

        if (!departementRepository.existsById(departementId)) {
            throw new IllegalArgumentException("Departement introuvable avec l'ID : " + departementId);
        }

        return villeRepository.findTopNVillesByDepartementId(departementId, n);
    }

    @Transactional(readOnly = true)
    public List<Ville> getVillesByPopulation(int departementId, int minPop, int maxPop) {
        if (minPop < 0 || maxPop < 0) {
            throw new IllegalArgumentException("Les populations min et max doivent être positives");
        }
        if (minPop > maxPop) {
            throw new IllegalArgumentException("La population minimum ne peut pas être superieure à la population maximum");
        }

        if (!departementRepository.existsById(departementId)) {
            throw new IllegalArgumentException("Departement introuvable avec l'ID : " + departementId);
        }

        return villeRepository.findByDepartementIdAndNbHabitantsBetween(departementId, minPop, maxPop);
    }

    private void validateDepartement(Departement departement) {
        if (departement.getNom() == null || departement.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du departement est obligatoire");
        }
    }
}