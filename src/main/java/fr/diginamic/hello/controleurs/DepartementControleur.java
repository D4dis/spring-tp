package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.models.Ville;
import fr.diginamic.hello.services.DepartementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departements")
public class DepartementControleur {

    @Autowired
    private DepartementService departementService;

    @GetMapping
    public List<Departement> getDepartements() {
        return departementService.extractDepartements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Departement> getDepartementById(@PathVariable int id) {
        try {
            Departement departement = departementService.extractDepartement(id);
            if (departement != null) {
                return ResponseEntity.ok(departement);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/top-villes/{n}")
    public ResponseEntity<List<Ville>> getTopNVilles(@PathVariable int id, @PathVariable int n) {
        try {
            List<Ville> villes = departementService.getTopNVilles(id, n);
            return ResponseEntity.ok(villes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}/villes-population")
    public ResponseEntity<List<Ville>> getVillesByPopulation(
            @PathVariable int id,
            @RequestParam int minPop,
            @RequestParam int maxPop) {
        try {
            List<Ville> villes = departementService.getVillesByPopulation(id, minPop, maxPop);
            return ResponseEntity.ok(villes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> addDepartement(@Valid @RequestBody Departement nouveau) {
        try {
            departementService.insertDepartement(nouveau);
            return ResponseEntity.ok("Département inséré avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne du serveur");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDepartement(@PathVariable int id, @Valid @RequestBody Departement maj) {
        try {
            departementService.modifierDepartement(id, maj);
            return ResponseEntity.ok("Département modifié avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne du serveur");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartement(@PathVariable int id) {
        try {
            departementService.supprimerDepartement(id);
            return ResponseEntity.ok("Département supprimé avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne du serveur");
        }
    }
}