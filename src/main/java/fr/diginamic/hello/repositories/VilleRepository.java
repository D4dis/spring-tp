package fr.diginamic.hello.repositories;

import fr.diginamic.hello.models.Ville;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VilleRepository extends JpaRepository<Ville, Integer> {

    Optional<Ville> findByNomIgnoreCase(String nom);

    List<Ville> findByNomStartingWithIgnoreCaseOrderByNomAsc(String prefix);

    List<Ville> findByNbHabitantsGreaterThanOrderByNbHabitantsDesc(int min);

    List<Ville> findByNbHabitantsBetweenOrderByNbHabitantsDesc(int min, int max);

    // Recherche des villes d'un departement avec population > min
    @Query("SELECT v FROM Ville v WHERE v.departement.id = :departementId AND v.nbHabitants > :min ORDER BY v.nbHabitants DESC")
    List<Ville> findByDepartementIdAndNbHabitantsGreaterThan(@Param("departementId") int departementId, @Param("min") int min);

    // Recherche des villes d'un departement avec population entre min et max
    @Query("SELECT v FROM Ville v WHERE v.departement.id = :departementId AND v.nbHabitants BETWEEN :min AND :max ORDER BY v.nbHabitants DESC")
    List<Ville> findByDepartementIdAndNbHabitantsBetween(@Param("departementId") int departementId, @Param("min") int min, @Param("max") int max);

    // Recherche des n villes les plus peuplees d'un departement
    @Query(value = "SELECT v FROM Ville v WHERE v.departement.id = :departementId ORDER BY v.nbHabitants DESC")
    List<Ville> findTopNVillesByDepartement(@Param("departementId") int departementId, Pageable pageable);

    // Methode utilitaire pour obtenir les top N villes d'un departement
    default List<Ville> findTopNVillesByDepartementId(int departementId, int limit) {
        return findTopNVillesByDepartement(departementId,
                org.springframework.data.domain.PageRequest.of(0, limit));
    }

    // Recherche paginee de toutes les villes
    Page<Ville> findAll(Pageable pageable);

    // Recherche par departement (utile pour les relations)
    List<Ville> findByDepartementIdOrderByNomAsc(int departementId);
}