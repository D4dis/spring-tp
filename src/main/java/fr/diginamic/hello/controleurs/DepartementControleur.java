package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.dto.DepartementDto;
import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.mappers.DepartementMapper;
import fr.diginamic.hello.mappers.VilleMapper;
import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.models.Ville;
import fr.diginamic.hello.services.DepartementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/departements")
public class DepartementControleur {

    @Autowired
    private DepartementService departementService;

    @Autowired
    private DepartementMapper departementMapper;

    @Autowired
    private VilleMapper villeMapper;

    @GetMapping
    public List<DepartementDto> getDepartements() {
        return departementService.extractDepartements().stream()
                .map(departementMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartementDto> getDepartementById(@PathVariable int id) {
        try {
            Departement departement = departementService.extractDepartement(id);
            if (departement != null) {
                return ResponseEntity.ok(departementMapper.toDto(departement));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/top-villes/{n}")
    public ResponseEntity<List<VilleDto>> getTopNVilles(@PathVariable int id, @PathVariable int n) {
        try {
            List<Ville> villes = departementService.getTopNVilles(id, n);
            List<VilleDto> villeDtos = villes.stream()
                    .map(villeMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(villeDtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}/villes-population")
    public ResponseEntity<List<VilleDto>> getVillesByPopulation(
            @PathVariable int id,
            @RequestParam int minPop,
            @RequestParam int maxPop) {
        try {
            List<Ville> villes = departementService.getVillesByPopulation(id, minPop, maxPop);
            List<VilleDto> villeDtos = villes.stream()
                    .map(villeMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(villeDtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> addDepartement(@Valid @RequestBody DepartementDto nouveau) {
        try {

            Departement departement = departementMapper.toEntity(nouveau);
            departementService.insertDepartement(departement);
            return ResponseEntity.ok("Département inséré avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne du serveur");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDepartement(@PathVariable int id, @Valid @RequestBody DepartementDto maj) {
        try {
            Departement departement = departementMapper.toEntity(maj);
            departementService.modifierDepartement(id, departement);
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