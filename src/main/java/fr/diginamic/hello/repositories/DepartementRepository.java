package fr.diginamic.hello.repositories;

import fr.diginamic.hello.models.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Integer> {

    Optional<Departement> findByNomIgnoreCase(String nom);

    Optional<Departement> findByCode(String code);

    boolean existsByNomIgnoreCase(String nom);

    boolean existsByCode(String code);

    // Recuperer un departement avec ses villes
    @Query("SELECT d FROM Departement d LEFT JOIN FETCH d.villes WHERE d.id = :id")
    Optional<Departement> findByIdWithVilles(@Param("id") int id);

    // Recuperer tous les departements avec leurs villes
    @Query("SELECT DISTINCT d FROM Departement d LEFT JOIN FETCH d.villes ORDER BY d.nom")
    java.util.List<Departement> findAllWithVilles();
}