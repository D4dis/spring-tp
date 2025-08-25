package fr.diginamic.hello.mappers;

import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.models.Ville;
import org.springframework.stereotype.Component;

@Component
public class VilleMapper {
    public VilleDto toDto(Ville ville) {
        if (ville == null) {
            return null;
        }
        VilleDto dto = new VilleDto();
        dto.setId(ville.getId());
        dto.setNom(ville.getNom());
        dto.setNbHabitants(ville.getNbHabitants());
        if (ville.getDepartement() != null) {
            dto.setCodeDepartement(ville.getDepartement().getCode());
            dto.setNomDepartement(ville.getDepartement().getNom());
        }
        return dto;
    }

    public Ville toEntity(VilleDto dto) {
        if (dto == null) {
            return null;
        }
        Ville ville = new Ville();
        ville.setId(dto.getId());
        ville.setNom(dto.getNom());
        ville.setNbHabitants(dto.getNbHabitants());
        return ville;
    }
}
