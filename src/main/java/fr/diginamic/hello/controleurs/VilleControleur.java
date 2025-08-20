package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.models.Ville;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    @GetMapping
    public List<Ville> getVilles() {
        List<Ville> villes = new ArrayList<>();
        villes.add(new Ville("Paris", 2000000));
        villes.add(new Ville("Lyon", 500000));
        villes.add(new Ville("Marseille", 800000));
        villes.add(new Ville("Toulouse", 400000));
        return villes;
    }
}
