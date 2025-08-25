package fr.diginamic.hello.mappers;

import fr.diginamic.hello.dto.DepartementDto;
import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.models.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DepartementMapper {
    @Autowired
    private VilleMapper villeMapper;

    public DepartementDto toDto(Departement departement) {
        if (departement == null) {
            return null;
        }
        DepartementDto dto = new DepartementDto();
        dto.setId(departement.getId());
        dto.setNom(departement.getNom());
        dto.setCode(departement.getCode());
        if (departement.getVilles() != null) {
            int totalHabitants = departement.getVilles().stream()
                    .mapToInt(Ville::getNbHabitants)
                    .sum();
            dto.setNbHabitants(totalHabitants);
            dto.setVilles(departement.getVilles().stream()
                    .map(villeMapper::toDto)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public Departement toEntity(DepartementDto dto) {
        if (dto == null) {
            return null;
        }
        Departement departement = new Departement();
        departement.setId(dto.getId());
        departement.setNom(dto.getNom());
        return departement;
    }
}
