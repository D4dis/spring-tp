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

    public VilleControleur() {
        villes.add(new Ville("Paris", 2000000));
        villes.add(new Ville("Lyon", 500000));
        villes.add(new Ville("Marseille", 800000));
        villes.add(new Ville("Toulouse", 400000));
        villes.add(new Ville("Montpellier", 300000));
    }

    @GetMapping
    public List<Ville> getVilles() {
        return villes;
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
}
