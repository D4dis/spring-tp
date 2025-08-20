package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.models.Ville;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    private List<Ville> villes = new ArrayList<>();
    private int compteurId = 1;

    public VilleControleur() {
        villes.add(new Ville(compteurId++, "Paris", 2000000));
        villes.add(new Ville(compteurId++, "Lyon", 500000));
        villes.add(new Ville(compteurId++, "Marseille", 800000));
        villes.add(new Ville(compteurId++, "Toulouse", 400000));
        villes.add(new Ville(compteurId++, "Montpellier", 300000));
    }

    @GetMapping
    public List<Ville> getVilles() {
        return villes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ville> getVilleById(@PathVariable int id) {
        for (Ville v : villes) {
            if (v.getId() == id) {
                return ResponseEntity.ok(v);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<String> addVille(@RequestBody Ville nouvelleVille) {
        for (Ville v : villes) {
            if (v.getNom().equalsIgnoreCase(nouvelleVille.getNom())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La ville existe deja");
            }
        }
        villes.add(nouvelleVille);
        return ResponseEntity.ok("Ville inseree avec succes");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVille(@PathVariable int id, @RequestBody Ville villeMaj) {
        for (Ville v : villes) {
            if (v.getId() == id) {
                v.setNom(villeMaj.getNom());
                v.setNbHabitants(villeMaj.getNbHabitants());
                return ResponseEntity.ok("Ville modifiee avec succes");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ville introuvable");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {
        boolean removed = false;
        for (int i = 0; i < villes.size(); i++) {
            if (villes.get(i).getId() == id) {
                villes.remove(i);
                removed = true;
                break;
            }
        }
        if (removed) {
            return ResponseEntity.ok("Ville supprimee avec succes");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ville introuvable");
    }
}
